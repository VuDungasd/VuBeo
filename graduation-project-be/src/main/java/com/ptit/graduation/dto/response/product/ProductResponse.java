package com.ptit.graduation.dto.response.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class ProductResponse implements Serializable{
  private String id;
  @org.springframework.data.elasticsearch.annotations.Field(name = "name")
  @JsonProperty("name")
  @Field(name = "name")
  private String name;

  @org.springframework.data.elasticsearch.annotations.Field(name = "slug")
  @JsonProperty("slug")
  @Field(name = "slug")
  private String slug;

  @org.springframework.data.elasticsearch.annotations.Field(name = "import_price")
  @JsonProperty("import_price")
  @Field(name = "import_price")
  private Long importPrice;

  @org.springframework.data.elasticsearch.annotations.Field(name = "selling_price")
  @JsonProperty("selling_price")
  @Field(name = "selling_price")
  private Long sellingPrice;


  @org.springframework.data.elasticsearch.annotations.Field(name = "image")
  @JsonProperty("image")
  @Field(name = "image")
  private String image;


  @org.springframework.data.elasticsearch.annotations.Field(name = "is_sale")
  @JsonProperty("is_sale")
  @Field(name = "is_sale")
  private Boolean isSale;

  @org.springframework.data.elasticsearch.annotations.Field(name = "description")
  @JsonProperty("description")
  @Field(name = "description")
  private String description;

  @org.springframework.data.elasticsearch.annotations.Field(name = "review")
  @JsonProperty("review")
  @Field(name = "review")
  private float review;

  @org.springframework.data.elasticsearch.annotations.Field(name = "location")
  @JsonProperty("location")
  @Field(name = "location")
  private String location;

  @org.springframework.data.elasticsearch.annotations.Field(name = "brand_name")
  @JsonProperty("brand_name")
  @Field(name = "brand_name")
  private String brandName;

  @org.springframework.data.elasticsearch.annotations.Field(name = "category_name")
  @JsonProperty("category_name")
  @Field(name = "category_name")
  private String categoryName;

  @org.springframework.data.elasticsearch.annotations.Field(name = "quantity")
  @JsonProperty("quantity")
  @Field(name = "quantity")
  private Long quantity;

  @org.springframework.data.elasticsearch.annotations.Field(name = "sold_quantity")
  @JsonProperty("sold_quantity")
  @Field(name = "sold_quantity")
  private Long soldQuantity;

  @Transient
  private int score;

  @org.springframework.data.elasticsearch.annotations.Field(name = "attribute")
  @JsonProperty("attribute")
  @Field(name = "attribute")
  private Object attribute;
}
