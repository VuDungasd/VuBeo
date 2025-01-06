package com.ptit.graduation.initialization;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.ptit.graduation.entity.product.*;
import com.ptit.graduation.service.product.*;
import com.ptit.graduation.utils.ConvertVietnameseToNormalText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitialization implements CommandLineRunner {
  private final CategoryService categoryService;
  private final LocationService locationService;
  private final BrandService brandService;
  private final Faker faker;
  private final ProductMongoService productMongoService;
  private final ProductESService productESService;

  @Override
  public void run(String... args) throws Exception {
    // initCategory();
    // initLocation();
    // initBrand();
    // initProduct();
  }

  // @Scheduled(fixedRate = 50000000)
  public void initCategory() throws IOException, CsvValidationException {
    List<CategoryMongo> categories = new ArrayList<>();

    try (CSVReader csvReader = new CSVReader(new FileReader("Generated_Categories.csv", StandardCharsets.UTF_8))) {
      String[] nextLine;
      boolean isHeader = true;

      while ((nextLine = csvReader.readNext()) != null) {
        if (isHeader) {
          isHeader = false;
          continue;
        }

        String categoryName = nextLine[0];

        CategoryMongo category = new CategoryMongo();
        category.setId(UUID.randomUUID().toString());
        category.setName(categoryName);
        category.setActive(true);

        categories.add(category);
      }

      categoryService.saveAll(categories);
    }
  }

  // @Scheduled(fixedRate = 50000000)
  public void initLocation() throws IOException, CsvValidationException {
    List<LocationMongo> locations = new ArrayList<>();

    try (CSVReader csvReader = new CSVReader(new FileReader("Generated_Location.csv", StandardCharsets.UTF_8))) {
      String[] nextLine;
      boolean isHeader = true;

      while ((nextLine = csvReader.readNext()) != null) {
        if (isHeader) {
          isHeader = false;
          continue;
        }

        String name = nextLine[0];

        LocationMongo location = new LocationMongo();
        location.setId(UUID.randomUUID().toString());
        location.setName(name);
        location.setActive(true);

        locations.add(location);
      }

      locationService.saveAll(locations);
    }
  }

  // @Scheduled(fixedRate = 50000000)
  public void initBrand() throws IOException, CsvValidationException {
    List<BrandMongo> brands = new ArrayList<>();

    try (CSVReader csvReader = new CSVReader(new FileReader("Generated_Brands.csv", StandardCharsets.UTF_8))) {
      String[] nextLine;
      boolean isHeader = true;

      while ((nextLine = csvReader.readNext()) != null) {
        if (isHeader) {
          isHeader = false;
          continue;
        }

        String name = nextLine[0];

        BrandMongo brand = new BrandMongo();
        brand.setId(UUID.randomUUID().toString());
        brand.setName(name);
        brand.setActive(true);

        brands.add(brand);
      }

      brandService.saveAll(brands);
    }
  }

  // @Scheduled(fixedRate = 50000000)
  public void initProduct() throws IOException, CsvException {

    List<BrandMongo> brands = brandService.list();
    List<CategoryMongo> categories = categoryService.list();
    List<LocationMongo> locations = locationService.list();

    if (brands.isEmpty()) {
      log.error("Brand list is empty.");
      return;
    }
    if (categories.isEmpty()) {
      log.error("Category list is empty.");
      return;
    }
    if (locations.isEmpty()) {
      log.error("Location list is empty.");
      return;
    }

    BrandMongo brand;
    CategoryMongo category;
    LocationMongo location;
    ProductMongo product;
    try (CSVReader csvReader = new CSVReader(
          new FileReader("Generated_Vietnamese_Prefixes.csv", StandardCharsets.UTF_8))) {
      List<String[]> prefixes = csvReader.readAll();
      ConvertVietnameseToNormalText convert = new ConvertVietnameseToNormalText();
      String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

      for (int i = 0; i < text.length(); i++) {
        List<ProductMongo> products = new ArrayList<>();
        List<ProductES> esProducts = new ArrayList<>();

        // 52000 * 26 * 9 = 12,168,000
        for (int j = 1; j < 10; j++) {
          for (String[] prefix : prefixes) {
            String name = prefix[0] + " " + text.charAt(i) + j;
            brand = brands.get(faker.number().numberBetween(0, brands.size() - 1));
            category = categories.get(faker.number().numberBetween(0, categories.size() - 1));
            location = locations.get(faker.number().numberBetween(0, locations.size() - 1));

            product = new ProductMongo();
            product.setId(UUID.randomUUID().toString());
            product.setName(name);
            product.setSlug(convert.slugify(name));
            product.setImportPrice(faker.number().numberBetween(100000L, 5000000L));
            product.setSellingPrice(product.getImportPrice() + faker.number().numberBetween(100000L, 500000L));
            product.setImage("https://picsum.photos/seed/" + j + "/200/300");
            product.setDescription(faker.lorem().sentence());
            product.setReview((float) faker.number().randomDouble(1, 1, 5));
            product.setIsSale(faker.bool().bool());
            product.setQuantity((long) faker.number().numberBetween(100, 50000));
            product.setSoldQuantity(faker.number().numberBetween(0, product.getQuantity()));
            product.setLocationId(location.getId());
            product.setLocation(location.getName());
            product.setBrandId(brand.getId());
            product.setBrandName(brand.getName());
            product.setCategoryId(category.getId());
            product.setCategoryName(category.getName());
            product.generateNGrams(3);

            products.add(product);

            ProductES productES = getProductES(product);

            esProducts.add(productES);
          }

        }

        try {
          productMongoService.bulkInsert(products);
          productESService.bulkInsert(esProducts);
        } catch (Exception e) {
          log.error("Error during data insertion", e);
        }
      }

    }
  }

  private ProductES getProductES(ProductMongo product) {
    ProductES productES = new ProductES();
    productES.setId(product.getId());
    productES.setName(product.getName());
    productES.setSlug(product.getSlug());
    productES.setImportPrice(product.getImportPrice());
    productES.setSellingPrice(product.getSellingPrice());
    productES.setImage(product.getImage());
    productES.setDescription(product.getDescription());
    productES.setReview(product.getReview());
    productES.setSale(product.getIsSale());
    productES.setQuantity(product.getQuantity());
    productES.setSoldQuantity(product.getSoldQuantity());
    productES.setLocationId(product.getLocationId());
    productES.setLocation(product.getLocation());
    productES.setBrandId(product.getBrandId());
    productES.setBrandName(product.getBrandName());
    productES.setCategoryId(product.getCategoryId());
    productES.setCategoryName(product.getCategoryName());
    return productES;
  }
}
