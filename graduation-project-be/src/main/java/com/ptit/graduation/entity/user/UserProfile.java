package com.ptit.graduation.entity.user;

import com.ptit.graduation.entity.base.BaseMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_profiles")
public class UserProfile extends BaseMongoEntity {

    @Field(name = "user_id")
    private String userId;

    @Field(name = "preferred_categories")
    private List<String> preferredCategories; // Danh mục yêu thích

    @Field(name = "preferred_brands")
    private List<String> preferredBrands; // Thương hiệu yêu thích

    @Field(name = "recently_viewed_products")
    private List<String> recentlyViewedProducts; // ID các sản phẩm đã xem gần đây
}
