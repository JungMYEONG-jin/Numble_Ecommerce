package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.dto.product.ProductListResponse;
import com.mj.webmarket.entity.dto.reply.ReplyListResponse;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.Reply;
import com.mj.webmarket.repository.product.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<Reply> getAllReviews(Pageable pageable) {
        return replyRepository.findAll(pageable);
    }

    @Override
    public List<Reply> getAllReviews() {
        return replyRepository.findAll();
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


    public ReplyListResponse toReplyResponseDto(Reply reply){
        String userImg = "/images/carrot.png";
        return ReplyListResponse.builder().comment(reply.getComment())
                .memberEmail(reply.getUser().getEmail())
                .memberProfile(reply.getUser().getUserImage()==null?userImg:reply.getUser().getUserImage().getServerFileName()).build();
    }

    public Page<ReplyListResponse> productToProductListResponseDtoPage(Pageable pageable, List<Reply> replies) {
        List<ReplyListResponse> replyListResponses = replies.stream().map(r -> toReplyResponseDto(r)).collect(Collectors.toList());
        log.info("productList size {}", replyListResponses.size());
        int start = (int) pageable.getOffset();
        log.info("start {} ", start);
        int end = Math.min((start + pageable.getPageSize()), replyListResponses.size());
        log.info("end {} ", end);
        return new PageImpl<>(replyListResponses.subList(start, end), pageable, replyListResponses.size());
    }

}
