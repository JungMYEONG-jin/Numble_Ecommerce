package com.mj.webmarket.entity.trade;

import com.mj.webmarket.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer tradeCount;
    private Integer donationCount;

    @Builder
    public Trade(User user, Integer tradeCount, Integer donationCount) {
        this.user = user;
        this.tradeCount = tradeCount;
        this.donationCount = donationCount;
    }

    public void addTradeCount(){
        this.tradeCount++;
    }

    public void deleteTradeCount(){
        this.tradeCount--;
    }

    public void addDonationCount(){
        this.donationCount++;
    }

    public void deleteDonationCount(){
        this.donationCount--;
    }

}
