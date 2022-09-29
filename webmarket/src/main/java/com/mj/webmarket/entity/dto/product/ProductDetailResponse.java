package com.mj.webmarket.entity.dto.product;

import com.mj.webmarket.entity.product.ProductImage;
import com.mj.webmarket.entity.product.ProductStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDetailResponse {

    private Long id;
    private String title;
    private String description;
    private Integer replyCount;
    private Integer heartCount;
    private Integer price;
    private ProductStatus productStatus;
    private Long categoryId;
    private List<String> productImages = new ArrayList<>();

    @Builder
    public ProductDetailResponse(Long id, String title, String description, Integer replyCount, Integer heartCount, Integer price, ProductStatus productStatus, Long categoryId, List<String> productImages) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.replyCount = replyCount;
        this.heartCount = heartCount;
        this.price = price;
        this.productStatus = productStatus;
        this.categoryId = categoryId;
        this.productImages = productImages;
    }

    public void addDefaultImage(){
        productImages.add("/images/logo.jpeg");
    }
}
