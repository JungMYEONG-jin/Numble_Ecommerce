package com.mj.webmarket.service.heart;

import com.mj.webmarket.entity.heart.Heart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HeartService {

    public Heart add(Heart heart);

    public List<Heart> getProductHeart(Long productId);
    public Page<Heart> getProductHeart(Long productId, Pageable pageable);

    public List<Heart> getUserHeart(Long userId);
    public Page<Heart> getUserHeart(Long userId, Pageable pageable);

    public void deleteAll(Long productId);
    public void deleteOne(Long ProductId, Long userId);

}
