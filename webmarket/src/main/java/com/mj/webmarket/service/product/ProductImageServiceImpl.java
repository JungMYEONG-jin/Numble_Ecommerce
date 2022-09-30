package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.dto.product.ProductRegisterRequest;
import com.mj.webmarket.entity.product.ProductImage;
import com.mj.webmarket.repository.product.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService{

    private final ProductImageRepository productImageRepository;

    @Transactional
    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }
}
