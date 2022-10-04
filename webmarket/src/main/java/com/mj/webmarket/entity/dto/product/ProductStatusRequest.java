package com.mj.webmarket.entity.dto.product;

import com.mj.webmarket.entity.product.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductStatusRequest {

    private ProductStatus productStatus;

    @Builder
    public ProductStatusRequest(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }
}
