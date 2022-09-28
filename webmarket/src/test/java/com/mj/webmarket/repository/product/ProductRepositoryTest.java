package com.mj.webmarket.repository.product;

import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.repository.category.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    @Test
    void findByCategoryTest() {
        Category category = new Category();
        category.setName("food");
        Category category2 = new Category();
        category2.setName("drink");
        Product product = Product.builder().price(3000).category(category).build();
        Product product2 = Product.builder().price(3000).category(category2).build();

        productRepository.save(product);
        productRepository.save(product2);

        Product byCategory = productRepository.findByCategory(category).orElse(null);
        Assertions.assertThat(byCategory.getCategory().getId()).isEqualTo(category.getId());
        Assertions.assertThat(byCategory.getCategory().getId()).isEqualTo(category2.getId());
    }

}