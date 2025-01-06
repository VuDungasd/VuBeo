package com.ptit.graduation.service.product.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.ptit.graduation.dto.request.product.ProductFilterRequest;
import com.ptit.graduation.dto.response.product.ProductPageResponse;
import com.ptit.graduation.dto.response.product.ProductResponse;
import com.ptit.graduation.entity.product.ProductES;
// import com.ptit.graduation.repository.product.ProductESRepository;
import com.ptit.graduation.service.product.ProductESService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ptit.graduation.utils.NormalTextSearch.normalize;
import static com.ptit.graduation.utils.Stopword.removeStopWords;
import static com.ptit.graduation.utils.VietnameseAccentMapper.convertToAccented;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductESServiceImpl implements ProductESService {
  // private final ProductESRepository repository;
  private final ElasticsearchClient elasticsearchClient;

  @Override
  public void bulkInsert(List<ProductES> productESList) {
    try {
      List<BulkOperation> operations = productESList.stream()
          .map(product -> BulkOperation.of(op -> op
              .index(idx -> idx
                  .index("products")
                  .id(product.getId())
                  .document(JsonData.of(product)))))
          .collect(Collectors.toList());

      BulkRequest request = BulkRequest.of(b -> b.operations(operations));

      BulkResponse response = elasticsearchClient.bulk(request);

      if (response.errors()) {
        log.error("Lỗi xảy ra khi bulkInsert: {}", response);
      } else {
        log.info("Đã chèn {} tài liệu vào Elasticsearch", productESList.size());
      }
    } catch (Exception e) {
      log.error("Lỗi khi thêm dữ liệu vào Elasticsearch", e);
    }
  }

  @Override
  public ProductPageResponse filter(ProductFilterRequest request) {
    log.info("(filter) request: {}", request);
    String keyword = request.getKeyword();
    keyword = normalize(keyword);
    keyword = convertToAccented(keyword);
    keyword = removeStopWords(keyword);
    log.info("keyword: {}", keyword);

    List<List<Query>> shouldFilters = new ArrayList<>();
    List<List<Query>> shouldFiltersWithFuzziness = new ArrayList<>();


    if (keyword != null && !keyword.isEmpty()) {
      String[] keywordArr = keyword.split(" ");
      for (int i = 0; i < keywordArr.length; i++) {
        String keywordTemp = String.join(" ", Arrays.asList(keywordArr).subList(0, keywordArr.length - i));
        log.info("keywordTemp: {}", keywordTemp);
        // tìm chính xác
        List<Query> filters = new ArrayList<>();
        filters.add(filterExactly(keywordTemp));
        shouldFilters.add(filters);

        // tìm với fuzziness
        List<Query> filtersWithFuzziness = new ArrayList<>();
        filtersWithFuzziness.add(filterWithFuzzinessAndOrder(keywordTemp));
        shouldFiltersWithFuzziness.add(filtersWithFuzziness);
      }
    }

    for (int i = 0; i < shouldFilters.size(); i++) {
      shouldFilters.get(i).add(filterReview(request.getReview()));
      shouldFiltersWithFuzziness.get(i).add(filterReview(request.getReview()));
    }

    if (request.getLocationIds() != null && !request.getLocationIds().isEmpty()) {
      for (int i = 0; i < shouldFilters.size(); i++) {
        shouldFilters.get(i).add(filterLocation(request.getLocationIds()));
        shouldFiltersWithFuzziness.get(i).add(filterLocation(request.getLocationIds()));
      }
    }

    if (request.getBrandId() != null && !request.getBrandId().isEmpty()) {
      for (int i = 0; i < shouldFilters.size(); i++) {
        shouldFilters.get(i).add(filterBrand(request.getBrandId()));
        shouldFiltersWithFuzziness.get(i).add(filterBrand(request.getBrandId()));
      }
    }

    if (request.getCategoryId() != null && !request.getCategoryId().isEmpty()) {
      for (int i = 0; i < shouldFilters.size(); i++) {
        shouldFilters.get(i).add(filterCategory(request.getCategoryId()));
        shouldFiltersWithFuzziness.get(i).add(filterCategory(request.getCategoryId()));
      }
    }

    if (request.getPriceFrom() != null && request.getPriceTo() != null) {
      for (int i = 0; i < shouldFilters.size(); i++) {
        shouldFilters.get(i).add(filterPrice(request.getPriceFrom(), request.getPriceTo()));
        shouldFiltersWithFuzziness.get(i).add(filterPrice(request.getPriceFrom(), request.getPriceTo()));
      }
    }
    log.info("shouldFilters: {}", shouldFilters);

    SortOrder sortOrder = SortOrder.Desc;
    String sortField = "review";

    sortOrder = switch (request.getSort()) {
      case 1 -> {
        sortField = "selling_price";
        yield SortOrder.Asc;
      }
      case 2 -> {
        sortField = "selling_price";
        yield SortOrder.Desc;
      }
      case 3 -> {
        sortField = "name";
        yield SortOrder.Asc;
      }
      default -> sortOrder;
    };

    int from = request.getPage() * request.getSize();
    int size = request.getSize();

    try {
      String finalSortField = sortField;
      SortOrder finalSortOrder = sortOrder;

      List<ProductES> products = new ArrayList<>();
      long countProduct = 0;
      
      for (List<Query> filters : shouldFilters) {
        CountResponse countResponse = elasticsearchClient.count(c -> c
            .index("products")
            .query(q -> q.bool(b -> b.filter(filters))));
        countProduct = countResponse.count();
        if (countProduct > 0) {
          SearchResponse<ProductES> response = elasticsearchClient.search(s -> s
              .index("products")
              .query(q -> q.bool(b -> b.filter(filters)))
              .from(from)
              .size(size)
              .sort(so -> so.field(f -> f
                  .field(finalSortField)
                  .order(finalSortOrder))),
              ProductES.class);

          for (Hit<ProductES> hit : response.hits().hits()) {
            products.add(hit.source());
          }
          return ProductPageResponse.of(this.toDTO(products), countProduct);
        }
      }
      
      if(countProduct == 0) {
        for (List<Query> filters : shouldFiltersWithFuzziness) {
          CountResponse countResponse = elasticsearchClient.count(c -> c
              .index("products")
              .query(q -> q.bool(b -> b.filter(filters))));
          countProduct = countResponse.count();
          if (countProduct > 0) {
            SearchResponse<ProductES> response = elasticsearchClient.search(s -> s
                .index("products")
                .query(q -> q.bool(b -> b.filter(filters)))
                .from(from)
                .size(size)
                .sort(so -> so.field(f -> f
                    .field(finalSortField)
                    .order(finalSortOrder))),
                ProductES.class);

            for (Hit<ProductES> hit : response.hits().hits()) {
              products.add(hit.source());
            }
            return ProductPageResponse.of(this.toDTO(products), countProduct);
          }
        }
      }
      return ProductPageResponse.of(this.toDTO(products), countProduct);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Query filterExactly(String keyword) {
    return Query.of(q -> q.bool(b -> b
        .should(Query.of(q1 -> q1.matchPhrase(mp -> mp
            .query(keyword)
            .field("name")
            .slop(0) // Không cho phép lệch từ hoặc sai thứ tự
        )))
        .should(Query.of(q2 -> q2.matchPhrase(mp -> mp
            .query(keyword)
            .field("brand_name")
            .slop(0))))
        .should(Query.of(q3 -> q3.matchPhrase(mp -> mp
            .query(keyword)
            .field("category_name")
            .slop(0))))
        .minimumShouldMatch("1") // Ít nhất một trường phải khớp
    ));
  }

  private Query filterWithFuzzinessAndOrder(String keyword) {
    String minimumShouldMatch = "100%";
    String[] keywords = keyword.split(" ");
    List<Query> shouldQueries = new ArrayList<>();

    for (String word : keywords) {
      String fuzziness;
      if (word.length() >= 2 && word.length() <= 5) {
        fuzziness = "1";
      } else {
        fuzziness = "AUTO";
      }

      // Tạo match query cho từng từ
      shouldQueries.add(Query.of(q -> q.match(m -> m
          .field("name") // Tìm kiếm trên trường "name"
          .query(word)
          .fuzziness(fuzziness))));
    }

    // Kết hợp các match query vào một bool query
    return Query.of(q -> q.bool(b -> b
        .should(shouldQueries) // Mỗi từ là một truy vấn "should"
        .minimumShouldMatch(minimumShouldMatch) // Đảm bảo tất cả các từ phải xuất hiện
    ));
  }

  private Query filterReview(Float review) {
    return Query.of(q -> q.range(r -> r
        .field("review")
        .gte(JsonData.of(review))));
  }

  private Query filterLocation(List<String> locationIds) {
    List<FieldValue> locationFieldValues = locationIds.stream()
        .map(FieldValue::of)
        .toList();

    return Query.of(q -> q.terms(t -> t
        .field("location_id.keyword")
        .terms(v -> v.value(locationFieldValues))));
  }

  private Query filterBrand(String brandId) {
    return Query.of(q -> q.term(t -> t
        .field("brand_id.keyword")
        .value(brandId)));
  }

  private Query filterCategory(String categoryId) {
    return Query.of(q -> q.term(t -> t
        .field("category_id.keyword")
        .value(categoryId)));
  }

  private Query filterPrice(Integer priceFrom, Integer priceTo) {
    return Query.of(q -> q.range(r -> {
      RangeQuery.Builder builder = r.field("selling_price");
      if (Objects.nonNull(priceFrom) && priceFrom > 0) {
        builder.gte(JsonData.of(priceFrom));
      }
      if (Objects.nonNull(priceTo) && priceTo > 0) {
        builder.lte(JsonData.of(priceTo));
      }
      return builder;
    }));
  }

  private List<ProductResponse> toDTO(List<ProductES> products) {
    List<ProductResponse> productDTOs = new ArrayList<>();

    for (ProductES productES : products) {
      productDTOs.add(
          ProductResponse.builder()
              .id(productES.getId())
              .name(productES.getName())
              .categoryName(productES.getCategoryName())
              .brandName(productES.getBrandName())
              .slug(productES.getSlug())
              .importPrice(productES.getImportPrice())
              .sellingPrice(productES.getSellingPrice())
              .image(productES.getImage())
              .description(productES.getDescription())
              .review(productES.getReview())
              .location(productES.getLocation())
              .brandName(productES.getBrandName())
              .categoryName(productES.getCategoryName())
              .quantity(productES.getQuantity())
              .soldQuantity(productES.getSoldQuantity())
              .attribute(productES.getAttribute())
              .isSale(productES.isSale())
              .build());
    }

    return productDTOs;
  }
}
