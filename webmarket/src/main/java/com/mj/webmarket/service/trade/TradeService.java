package com.mj.webmarket.service.trade;

import com.mj.webmarket.entity.trade.Trade;

public interface TradeService {
    /**
     * 사용자 거래 정보를 저장합니다.
     * @param trade 저장할 거래 정보
     * @return 저장된 거래 정보
     */
    Trade save(Trade trade);

    /**
     * 사용자 거래정보를 조회합니다.
     * @param memberId 조회할 회원아이디
     * @return 조회된 거래정보
     */
    Trade findTrade(Long memberId);

    /**
     * 거래 횟수 하나를 증가시킵니다.
     */
    void addTradeQuantity(Trade trade);

    /**
     * 거래 횟수 하나를 감소시킵니다.
     */
    void deleteTradeQuantity(Trade trade);

    /**
     * 기부 횟수 하나를 증가시킵니다.
     */
    void addDonationQuantity(Trade trade);

    /**
     * 기부 횟수 하나를 감소시킵니다.
     */
    void deleteDonationQuantity(Trade trade);
}
