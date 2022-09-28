package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> searchProductByCondition(ProductSearchForm form, Pageable pageable);
    List<Product> searchProductByCondition(ProductSearchForm form);
}
