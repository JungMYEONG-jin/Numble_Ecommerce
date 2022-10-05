package com.mj.webmarket.service.trade;

import com.mj.webmarket.entity.trade.Trade;
import com.mj.webmarket.entity.trade.TradeInit;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.repository.trade.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService{

    private final TradeRepository tradeRepository;

    @Transactional
    @Override
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public Trade findTrade(Long userId) {
        return tradeRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public void addTradeQuantity(Trade trade) {
        trade.addTradeCount();
    }

    @Transactional
    @Override
    public void deleteTradeQuantity(Trade trade) {
        trade.deleteTradeCount();
    }

    @Transactional
    @Override
    public void addDonationQuantity(Trade trade) {
        trade.addDonationCount();
    }

    @Transactional
    @Override
    public void deleteDonationQuantity(Trade trade) {
        trade.deleteDonationCount();
    }

    public Trade toTrade(User user){
        return Trade.builder().user(user).tradeCount(TradeInit.TRADE_QUANTITY).donationCount(TradeInit.DONATION_QUANTITY).build();
    }

}
