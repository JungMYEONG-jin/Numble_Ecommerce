package com.mj.webmarket.service.heart;

import com.mj.webmarket.entity.heart.Heart;
import com.mj.webmarket.repository.heart.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{

    private final HeartRepository heartRepository;

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
    public void deleteAll(Long productId) {
        heartRepository.deleteByProductId(productId);
    }

    @Override
    public void deleteOne(Long productId, Long userId) {
        heartRepository.deleteByProductIdAndUserId(productId, userId);
    }

}
