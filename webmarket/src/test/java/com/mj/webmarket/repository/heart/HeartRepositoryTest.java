package com.mj.webmarket.repository.heart;

import com.mj.webmarket.entity.heart.Heart;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.repository.product.ProductRepository;
import com.mj.webmarket.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HeartRepositoryTest {

    @Autowired
    HeartRepository heartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Test
    void heartInsertTest() {
        Product product = productRepository.findById(1L).orElse(null);
        User user = userRepository.findById(1L).orElse(null);
        Heart heart = Heart.builder().user(user).product(product).productInfo(product.getId()).build();
        Heart save = heartRepository.save(heart);
        Assertions.assertThat(save.getId()).isEqualTo(heart.getId());
    }

    @Transactional
    @Test
    void heartDeleteByProductAndUser(){
        Product product = productRepository.findById(1L).orElse(null);
        Product product2 = productRepository.findById(2L).orElse(null);
        User user = userRepository.findById(1L).orElse(null);
        Heart heart = Heart.builder().user(user).product(product).productInfo(product.getId()).build();
        Heart heart2 = Heart.builder().user(user).product(product2).productInfo(product.getId()).build();
        Heart save = heartRepository.save(heart);
        Heart save2 = heartRepository.save(heart2);
        heartRepository.deleteByProductIdAndUserId(product.getId(), user.getId());
        List<Heart> all = heartRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    void heartDeleteByProduct(){
        Product product = productRepository.findById(1L).orElse(null);
        Product product2 = productRepository.findById(2L).orElse(null);
        User user = userRepository.findById(1L).orElse(null);
        User user2 = userRepository.findById(2L).orElse(null);
        Heart heart = Heart.builder().user(user).product(product).productInfo(product.getId()).build();
        Heart heart2 = Heart.builder().user(user2).product(product).productInfo(product.getId()).build();
        Heart save = heartRepository.save(heart);
        Heart save2 = heartRepository.save(heart2);

        heartRepository.deleteByProductId(product.getId());

        List<Heart> all = heartRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(0);
    }

    @Transactional
    @Test
    void findByProductId(){
        Product product = productRepository.findById(1L).orElse(null);
        Product product2 = productRepository.findById(2L).orElse(null);
        User user = userRepository.findById(1L).orElse(null);
        User user2 = userRepository.findById(2L).orElse(null);
        Heart heart = Heart.builder().user(user).product(product).productInfo(product.getId()).build();
        Heart heart2 = Heart.builder().user(user2).product(product).productInfo(product.getId()).build();
        Heart save = heartRepository.save(heart);
        Heart save2 = heartRepository.save(heart2);
        List<Heart> all = heartRepository.findByProductId(product.getId());
        Assertions.assertThat(all.size()).isEqualTo(2);
    }

    @Transactional
    @Test
    void findByUserId(){
        Product product = productRepository.findById(1L).orElse(null);
        Product product2 = productRepository.findById(2L).orElse(null);
        User user = userRepository.findById(1L).orElse(null);
        Heart heart = Heart.builder().user(user).product(product).productInfo(product.getId()).build();
        Heart heart2 = Heart.builder().user(user).product(product2).productInfo(product.getId()).build();
        Heart save = heartRepository.save(heart);
        Heart save2 = heartRepository.save(heart2);
        List<Heart> all = heartRepository.findByUserId(user.getId());
        Assertions.assertThat(all.size()).isEqualTo(2);
    }

}