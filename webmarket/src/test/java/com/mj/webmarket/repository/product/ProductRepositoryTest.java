package com.mj.webmarket.repository.product;

import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.ProductStatus;
import com.mj.webmarket.entity.type.CategoryType;
import com.mj.webmarket.repository.category.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findByCategoryTest() {
        List<Category> collect = Arrays.stream(CategoryType.values()).map(categoryType -> Category.builder().name(categoryType.name()).id(categoryType.getId().longValue()).build()).collect(Collectors.toList());
        categoryRepository.saveAll(collect);
    }

}