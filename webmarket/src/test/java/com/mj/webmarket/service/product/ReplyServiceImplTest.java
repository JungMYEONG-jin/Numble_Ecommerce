package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.Reply;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.service.user.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ReplyServiceImplTest {

    @Autowired
    ReplyServiceImpl replyService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    UserServiceImpl userService;

    @Transactional
    @Rollback(value = false)
    @Test
    void insertTest() {

        User user = userService.findUser("shbebiz@gmail.com");
        Product product1 = productService.findOneById(1L);
        Product product2 = productService.findOneById(2L);

        for(int i=1;i<=20;i++){
            if(i%2==1){
                replyService.saveReview(Reply.builder().comment("저번에 맛있었는데 별로네요...").product(product1).user(user).build());
            }else{
                replyService.saveReview(Reply.builder().comment("치킨에 기름이 많아요..").product(product2).user(user).build());
            }

        }
    }

    @Transactional
    @Test
    void deleteByProductId(){
        List<Reply> replies = replyService.showProductReviewsWithoutPaging(1L);
        System.out.println("replies = " + replies.size());
        replyService.deleteProductReviewAll(1L);
        replies = replyService.showProductReviewsWithoutPaging(1L);
        Assertions.assertThat(replies.size()).isEqualTo(0);
    }

    @Transactional
    @Test
    void deleteByUserId(){
        replyService.deleteUserReviewAll(1L);
        List<Reply> reviews = replyService.getAllReviews();
        Assertions.assertThat(reviews.size()).isEqualTo(0);
    }

    @Transactional
    @Test
    void deleteByProductAndUser(){
        replyService.deleteProductReview(1L, 1L);
        List<Reply> reviews = replyService.getAllReviews();
        Assertions.assertThat(reviews.size()).isEqualTo(10);
    }
}