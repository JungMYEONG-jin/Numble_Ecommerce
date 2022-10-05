package com.mj.webmarket.repository.trade;

import com.mj.webmarket.entity.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    /**
     * one to one mapping 이라 단건 조회
     * @param userId
     * @return
     */
    Trade findByUserId(Long userId);
}
