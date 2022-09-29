package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.product.Reply;
import com.mj.webmarket.repository.product.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    @Override
    public Page<Reply> showProductReviews(Long productId, Pageable pageable) {
        return replyRepository.findByProductId(productId, pageable);
    }

    @Override
    public List<Reply> showProductReviewsWithoutPaging(Long productId) {
        return replyRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public void deleteProductReviewAll(Long productId) {
        replyRepository.deleteByProductId(productId);
    }

    @Override
    @Transactional
    public void deleteUserReviewAll(Long userId) {
        replyRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteProductReview(Long productId, Long userId) {
        replyRepository.deleteByProductIdAndUserId(productId, userId);
    }

    @Override
    @Transactional
    public void saveReview(Reply reply) {
        replyRepository.save(reply);
    }

    @Override
    @Transactional
    public void saveAllReviews(List<Reply> replyList) {
        replyRepository.saveAll(replyList);
    }

}
