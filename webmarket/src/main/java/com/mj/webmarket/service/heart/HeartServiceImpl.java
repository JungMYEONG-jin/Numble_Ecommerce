package com.mj.webmarket.service.heart;

import com.mj.webmarket.entity.dto.product.ProductListResponse;
import com.mj.webmarket.entity.heart.Heart;
import com.mj.webmarket.entity.product.ProductStatus;
import com.mj.webmarket.repository.heart.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{

    private final HeartRepository heartRepository;

    @Transactional
    @Override
    public Heart add(Heart heart) {
        return heartRepository.save(heart);
    }

    @Override
    public List<Heart> getProductHeart(Long productId) {
        return heartRepository.findByProductId(productId);
    }

    @Override
    public Page<Heart> getProductHeart(Long productId, Pageable pageable) {
        return heartRepository.findByProductId(productId, pageable);
    }

    @Override
    public List<Heart> getUserHeart(Long userId) {
        return heartRepository.findByUserId(userId);
    }

    @Override
    public Page<Heart> getUserHeart(Long userId, Pageable pageable) {
        return heartRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<ProductListResponse> getMyHeartProducts(Long userId) {
        List<Heart> byUserId = heartRepository.findByUserId(userId);
        return byUserId.stream().map(heart -> ProductListResponse.builder().id(heart.getProduct().getId()).productStatus(heart.getProduct().getProductStatus())
                .heartCount(heart.getProduct().getHeartCount()).replyCount(heart.getProduct().getReplyCount()).price(heart.getProduct().getPrice()).title(heart.getProduct().getTitle()).thumbnailImage(heart.getProduct().getProductImages().size() == 0 ? "/images/chicken.jpeg" : heart.getProduct().getProductImages().get(0).getServerFileName()).build()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteAll(Long productId) {
        heartRepository.deleteByProductId(productId);
    }

    @Transactional
    @Override
    public void deleteOne(Long productId, Long userId) {
        heartRepository.deleteByProductIdAndUserId(productId, userId);
    }

}
