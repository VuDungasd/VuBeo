package com.ptit.graduation.service.product.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import com.ptit.graduation.dto.request.product.ProductFilterRequest;
import com.ptit.graduation.dto.response.product.ProductListResponse;
import com.ptit.graduation.dto.response.product.ProductPageResponse;
import com.ptit.graduation.dto.response.product.ProductResponse;
import com.ptit.graduation.entity.product.LocationMongo;
import com.ptit.graduation.entity.product.ProductMongo;
import com.ptit.graduation.repository.product.ProductMongoRepository;
import com.ptit.graduation.service.base.impl.BaseServiceImpl;
import com.ptit.graduation.service.product.LocationService;
import com.ptit.graduation.service.product.ProductMongoService;
import com.ptit.graduation.service.product.ProductRedisService;
import com.ptit.graduation.utils.ConvertVietnameseToNormalText;
import com.ptit.graduation.utils.NgramUtils;
import com.ptit.graduation.utils.NormalTextSearch;
import com.ptit.graduation.utils.VietnameseAccentMapper;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.NGRAM_COUNT;
import static com.ptit.graduation.constants.GraduationProjectConstants.CommonConstants.LOCATIONS_POPULAR;
import static com.ptit.graduation.utils.Stopword.removeStopWords;

@Slf4j
@Service
public class ProductMongoServiceImpl extends BaseServiceImpl<ProductMongo> implements ProductMongoService {
  private final ProductMongoRepository repository;
  private final MongoTemplate mongoTemplate;
  @Autowired
  private ProductRedisService productRedisService;
  @Autowired
  private LocationService locationService;

  public ProductMongoServiceImpl(ProductMongoRepository repository, MongoTemplate mongoTemplate) {
    super(repository);
    this.repository = repository;
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void bulkInsert(List<ProductMongo> products) {
    MongoCollection<Document> collection = mongoTemplate.getCollection("products");

    List<WriteModel<Document>> operations = new ArrayList<>();
    for (ProductMongo product : products) {
      Document document = (Document) mongoTemplate.getConverter().convertToMongoType(product);
      if (document != null) {
        operations.add(new InsertOneModel<>(document));
      }
    }

    collection.bulkWrite(operations, new BulkWriteOptions().ordered(false));
    log.info("Inserted {} products using bulk operations", products.size());
  }

  @Override
  public ProductPageResponse filter(ProductFilterRequest request) {
    log.info("(filter) request: {}", request);
    String keyword = request.getKeyword();
    keyword = NormalTextSearch.normalize(keyword);
    keyword = VietnameseAccentMapper.convertToAccented(keyword);
    keyword = removeStopWords(keyword);
    keyword = fuzzySearch(keyword);

    List<String> ngrams = NgramUtils.createNGrams(keyword, NGRAM_COUNT);
    log.info("ngrams: {}", ngrams);

    List<String> locationIds = request.getLocationIds();
    if (locationIds == null || locationIds.isEmpty()) {
      locationIds = new ArrayList<>();
      // for (String location : LOCATIONS_POPULAR) {
      //   locationIds.add(locationService.findByName(location).getId());
      // }
      for (LocationMongo location : locationService.list()) {
        locationIds.add(location.getId());
      }
      log.info("locationIds: {}", locationIds);
    }

    Criteria criteria = new Criteria().and("ngrams").all(ngrams)
        .and("review").gte(request.getReview())
        .and("location_id").in(locationIds);

    if (request.getBrandId() != null && !request.getBrandId().isEmpty()) {
      criteria = criteria.and("brand_id").is(request.getBrandId());
    }

    if (request.getCategoryId() != null && !request.getCategoryId().isEmpty()) {
      criteria = criteria.and("category_id").is(request.getCategoryId());
    }

    if (request.getPriceFrom() != null || request.getPriceTo() != null) {
      List<Criteria> priceCriteria = new ArrayList<>();
      if (request.getPriceFrom() != null && request.getPriceFrom() > 0) {
        priceCriteria.add(Criteria.where("selling_price").gte(request.getPriceFrom()));
      }
      if (request.getPriceTo() != null && request.getPriceTo() > 0) {
        priceCriteria.add(Criteria.where("selling_price").lte(request.getPriceTo()));
      }
      criteria = criteria.andOperator(priceCriteria.toArray(new Criteria[0]));
    }

    Sort sort = Sort.by(Sort.Direction.DESC, "review");

    if (request.getSort() == 1) {
      sort = Sort.by(Sort.Direction.ASC, "selling_price");
    } else if (request.getSort() == 2) {
      sort = Sort.by(Sort.Direction.DESC, "selling_price");
    }

    Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

    Query query = new Query(criteria).with(pageable);

    List<ProductResponse> products = mongoTemplate.find(query, ProductResponse.class, "products");
    long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), ProductResponse.class, "products");

    for (ProductResponse product : products) {
      int score = 0;
      for (String ngram : ngrams) {
        if (product.getName().contains(ngram)) {
          score += 10;
        }
        if (product.getBrandName().contains(ngram)) {
          score += 5;
        }
        if (product.getCategoryName().contains(ngram)) {
          score += 3;
        }
      }
      product.setScore(score);
    }

    products.sort(Comparator.comparing(ProductResponse::getScore).reversed());
    return ProductPageResponse.of(products, total);
  }

  private String fuzzySearch(String keyword) {
    log.info("fuzzySearch: {}", keyword);
    ConvertVietnameseToNormalText convert = new ConvertVietnameseToNormalText();
    List<Object> keysRedis = productRedisService.getKeys();

    String[] keywordArr = keyword.split(" ");
    for (int i = 0; i < keywordArr.length; i++) {
      String text = String.join(" ", Arrays.asList(keywordArr).subList(i, keywordArr.length));
      List<String> keyList = new ArrayList<>();
      for (Object obj : keysRedis) {
        if (obj instanceof Map<?, ?>) {
          Map<?, ?> map = (Map<?, ?>) obj;
          if (map.containsKey(text.substring(0, 1))) {
            keyList.addAll((List<String>) map.get(convert.toNonAccentVietnamese(text.substring(0, 1))));
          }
        }
      }

      int start = 0;
      while (start < text.length()) {
        String keywordTemp = text.substring(0, text.length() - start).trim();
        String keywordEnglishTemp = convert.slugify(keywordTemp);
        start++;

        for (String key : keyList) {
          String[] keyArr = key.split("&&");
          if (keyArr[0].startsWith(keywordTemp) || keyArr[1].startsWith(keywordEnglishTemp)) {
            int len = keywordTemp.split(" ").length;
            return String.join(" ", Arrays.asList(keyArr[0].split(" ")).subList(0, len));
          }
        }
      }

    }
    return keyword;
  }

  @Override
  public ProductListResponse getAll(int skip, int limit) {
    try {
      log.info("(getAll) skip: {}, limit: {}", skip, limit);
      if (productRedisService.checkProductsExist()) {
        List<ProductResponse> products = productRedisService.getAll(skip, limit);
        long total = products.size();
        return ProductListResponse.of(products, total);
      }
      List<ProductMongo> products = repository.getAll(skip, limit);
      List<ProductResponse> productResponses = products.stream()
          .map(product -> convertProductMongoToProductResponse(product))
          .collect(Collectors.toList());
      long total = products.size();
      return ProductListResponse.of(productResponses, total);
    } catch (Exception e) {
      log.error("Error: {}", e.getMessage());
      return ProductListResponse.of(new ArrayList<>(), 0);
    }
  }

  @Override
  public ProductResponse convertProductMongoToProductResponse(ProductMongo product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .slug(product.getSlug())
        .importPrice(product.getImportPrice())
        .sellingPrice(product.getSellingPrice())
        .image(product.getImage())
        .isSale(product.getIsSale())
        .description(product.getDescription())
        .review(product.getReview())
        .location(product.getLocation())
        .brandName(product.getBrandName())
        .categoryName(product.getCategoryName())
        .quantity(product.getQuantity())
        .soldQuantity(product.getSoldQuantity())
        .build();
  }

  @Override
  public ProductListResponse getByCategoryId(String categoryId, int skip, int limit) {
    log.info("(getByCategoryId) categoryId: {}, skip: {}, limit: {}", categoryId, skip, limit);

    List<ProductMongo> products = repository.findByCategoryId(categoryId, skip, limit);

    List<ProductResponse> productResponses = products.stream()
          .map(this::convertProductMongoToProductResponse)
          .collect(Collectors.toList());

    return ProductListResponse.of(productResponses, productResponses.size());
  }

}
