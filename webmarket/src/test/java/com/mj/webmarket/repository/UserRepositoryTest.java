package com.mj.webmarket.repository;

import com.mj.webmarket.entity.Product;
import com.mj.webmarket.entity.User;
import com.mj.webmarket.entity.type.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    void insertTest() {
        User user = new User();
        user.setName("mj");
        user.setGender(Gender.MALE);
        Product product = new Product();
        user.addProduct(product);
        productRepository.save(product);
        userRepository.save(user);
    }
}