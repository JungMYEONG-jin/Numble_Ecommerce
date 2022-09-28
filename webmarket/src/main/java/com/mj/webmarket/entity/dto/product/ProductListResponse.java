package com.mj.webmarket.entity.dto.product;

import com.mj.webmarket.entity.product.ProductStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 상품 리스트 응답 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductListResponse {
    private Long id;
    private String title;
    private int price;
    private int heartCount;
    private int replyCount;
    private ProductStatus productStatus;
    private String thumbnailImage;

    @Builder
    public ProductListResponse(Long id, String title, int price, int heartCount, int replyCount, ProductStatus productStatus, String thumbnailImage) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.heartCount = heartCount;
        this.replyCount = replyCount;
        this.productStatus = productStatus;
        this.thumbnailImage = thumbnailImage;
    }
}
