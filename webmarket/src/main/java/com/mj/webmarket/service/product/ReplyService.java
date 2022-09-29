package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.product.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {

    Page<Reply> showProductReviews(Long productId, Pageable pageable);
    List<Reply> showProductReviewsWithoutPaging(Long productId);

    void deleteProductReviewAll(Long productId);
    void deleteUserReviewAll(Long userId);
    void deleteProductReview(Long productId, Long userId);

    void saveReview(Reply reply);
    void saveAllReviews(List<Reply> replyList);

}
