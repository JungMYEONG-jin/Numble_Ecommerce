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

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findByCategoryTest() {
        Category category = new Category();
        category.setId(CategoryType.음식.getId().longValue());
        category.setName(CategoryType.음식.name());
        categoryRepository.save(category);

        for(int i=1;i<=100;i++){
            if(i%2==1){
                Product product = Product.builder().price(16000).category(category).heartCount(0).replyCount(0).productStatus(ProductStatus.TRADING).title("치킨").build();
                productRepository.save(product);
            }else if(i%2==0){
                Product product = Product.builder().price(22000).category(category).heartCount(0).replyCount(0).productStatus(ProductStatus.TRADING).title("돈까스").build();
                productRepository.save(product);
            }
        }

    }

}