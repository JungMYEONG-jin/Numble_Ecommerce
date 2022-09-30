package com.mj.webmarket.repository;

import com.mj.webmarket.repository.product.ProductRepository;
import com.mj.webmarket.repository.user.UserRepository;
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
    void extractNumber() {
        String num = "2017-12-18T23:36:37-08:00";
        String s = num.replaceAll("[^0-9]+", "");
        System.out.println("s = " + s);
    }
}