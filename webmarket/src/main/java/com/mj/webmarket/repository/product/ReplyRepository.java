package com.mj.webmarket.repository.product;

import com.mj.webmarket.entity.product.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByProductId(Long productId, Pageable pageable);
    List<Reply> findByProductId(Long productId);
    void deleteByProductId(Long productId);
    void deleteByUserId(Long userId);
    void deleteByProductIdAndUserId(Long productId, Long userId);



}
