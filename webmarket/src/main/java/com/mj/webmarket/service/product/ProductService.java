package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.dto.product.ProductDetailResponse;
import com.mj.webmarket.entity.dto.product.ProductListResponse;
import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> searchProductByCondition(ProductSearchForm form, Pageable pageable);
    List<Product> searchProductByCondition(ProductSearchForm form);
    List<ProductListResponse> getUserProductList(Long userId);
    List<ProductListResponse> getUserCanSellProductList(Long userId);
    List<ProductListResponse> getUserCompletedProduct(Long userId);
    Product findOneById(Long productId);
    ProductDetailResponse toProductResponseDto(Product product);
    void addProduct(Product product);
    void deleteProduct(Long productId);
    void addHeartCount(Product product);
    void decreaseHeartCount(Product product);
    void addReplyCount(Product product);
    void decreaseReplyCount(Product product);
    void changeProductStatus(Product product, ProductStatus productStatus);
    void deleteProduct(Long productId, Long userId);

}
