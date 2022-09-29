package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.product.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {


    // product ID 로 가져오기
    Page<Reply> showProductReviews(Long productId, Pageable pageable);
    List<Reply> showProductReviewsWithoutPaging(Long productId);

    // 모든 리뷰 가져오기
    Page<Reply> getAllReviews(Pageable pageable);
    List<Reply> getAllReviews();

    // 삭제하기
    void deleteProductReviewAll(Long productId);
    void deleteUserReviewAll(Long userId);
    void deleteProductReview(Long productId, Long userId);

    // 저장
    void saveReview(Reply reply);
    void saveAllReviews(List<Reply> replyList);


}
