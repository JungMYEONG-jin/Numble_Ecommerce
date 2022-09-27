package com.mj.webmarket.repository;

import com.mj.webmarket.repository.product.ProductRepository;
import com.mj.webmarket.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

}