package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.dto.product.ProductDetailResponse;
import com.mj.webmarket.entity.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductServiceImpl productService;

    @Transactional
    @Test
    void dtoTest() {
        Product findedProduct = productService.findOneById(1L);
        ProductDetailResponse productDetailResponse = productService.toProductResponseDto(findedProduct);
        System.out.println("productDetailResponse = " + productDetailResponse);

    }
}