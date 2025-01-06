package com.ptit.graduation.entity.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ptit.graduation.entity.base.BaseESEntity;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "products")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductES extends BaseESEntity {

  @Field(name = "name", type = FieldType.Text, analyzer = "standard")
  private String name;

  @Field(name = "slug", type = FieldType.Text, analyzer = "standard")
  private String slug;

  @Field(name = "import_price", type = FieldType.Long)
  private Long importPrice;

  @Field(name = "selling_price", type = FieldType.Long)
  private Long sellingPrice;

  @Field(name = "image", type = FieldType.Keyword)
  private String image;

  @Field(name = "is_sale", type = FieldType.Boolean)
  private boolean isSale;

  @Field(name = "description", type = FieldType.Text)
  private String description;

  @Field(name = "review", type = FieldType.Float)
  private float review;

  @Field(name = "location_id", type = FieldType.Keyword)
  private String locationId;

  @Field(name = "location", type = FieldType.Keyword)
  private String location;

  @Field(name = "brand_name", type = FieldType.Text, analyzer = "standard")
  private String brandName;

  @Field(name = "brand_id", type = FieldType.Keyword)
  private String brandId;

  @Field(name = "category_name", type = FieldType.Text, analyzer = "standard")
  private String categoryName;

  @Field(name = "category_id", type = FieldType.Keyword)
  private String categoryId;

  @Field(name = "quantity", type = FieldType.Long)
  private long quantity;

  @Field(name = "sold_quantity", type = FieldType.Long)
  private long soldQuantity;

  @Field(name = "attribute", type = FieldType.Object)
  private Object attribute;

  @Field(name = "ngrams", type = FieldType.Text, analyzer = "standard")
  private List<String> ngrams;
}

// PUT /products
// {
//   "settings": {
//     "analysis": {
//       "tokenizer": {
//         "edge_ngram": {
//           "type": "edge_ngram",
//           "min_gram": 1,
//           "max_gram": 25,
//           "token_chars": ["letter", "digit"]
//         }
//       },
//       "analyzer": {
//         "autocomplete": {
//           "type": "custom",
//           "tokenizer": "edge_ngram",
//           "filter": ["lowercase"]
//         }
//       }
//     }
//   },
//   "mappings": {
//     "properties": {
//       "name": {
//         "type": "text",
//         "analyzer": "standard"
//       },
//       "description": {
//         "type": "text"
//       },
//       "slug": {
//         "type": "text",
//         "analyzer": "standard"
//       },
//       "category_name": {
//         "type": "text",
//         "analyzer": "standard"
//       },
//       "location_id": {
//         "type": "keyword"
//       },
//       "category_id": {
//         "type": "keyword"
//       },
//       "brand_name": {
//         "type": "text",
//         "analyzer": "standard"
//       },
//       "brand_id": {
//         "type": "keyword"
//       },
//       "ngrams": {
//         "type": "text",
//         "analyzer": "standard"
//       }
//     }
//   }
// }

