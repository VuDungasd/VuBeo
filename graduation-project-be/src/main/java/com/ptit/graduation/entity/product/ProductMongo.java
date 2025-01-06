package com.ptit.graduation.entity.product;


import com.ptit.graduation.entity.base.BaseMongoEntity;
import com.ptit.graduation.utils.NgramUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductMongo extends BaseMongoEntity {
  @Field(name = "name")
  private String name;

  @Field(name = "slug")
  private String slug;

  @Field(name = "import_price")
  private Long importPrice;

  @Field(name = "selling_price")
  private Long sellingPrice;

  @Field(name = "image")
  private String image;

  @Field(name = "is_sale")
  private Boolean isSale;

  @Field(name = "description")
  private String description;

  @Field(name = "review")
  private float review;

  @Field(name = "location_id")
  private String locationId;

  @Field(name = "location")
  private String location;

  @Field(name = "brand_name")
  private String brandName;

  @Field(name = "brand_id")
  private String brandId;

  @Field(name = "category_name")
  private String categoryName;

  @Field(name = "category_id")
  private String categoryId;

  @Field(name = "quantity")
  private Long quantity;

  @Field(name = "sold_quantity")
  private Long soldQuantity;

  @Field(name = "attribute")
  private Object attribute;

  @Field(name = "ngrams")
  private List<String> ngrams;

  public void generateNGrams(int n) {
    ngrams = new ArrayList<>();
    if (name != null) {
      ngrams.addAll(NgramUtils.createNGrams(name, n));
    }
    if (brandName != null) {
      ngrams.addAll(NgramUtils.createNGrams(brandName, n));
    }
    if (categoryName != null) {
      ngrams.addAll(NgramUtils.createNGrams(categoryName, n));
    }
  }
}
