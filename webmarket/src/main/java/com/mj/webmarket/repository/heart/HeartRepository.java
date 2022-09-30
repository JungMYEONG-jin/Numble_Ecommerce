package com.mj.webmarket.repository.heart;

import com.mj.webmarket.entity.heart.Heart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    public void deleteByProductId(Long productId);
    public void deleteByProductIdAndUserId(Long productId, Long userId);

    public List<Heart> findByProductId(Long productId);
    public Page<Heart> findByProductId(Long productId, Pageable pageable);

    public List<Heart> findByUserId(Long userId);
    public Page<Heart> findByUserId(Long userId, Pageable pageable);

}
