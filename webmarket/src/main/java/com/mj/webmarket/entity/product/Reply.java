package com.mj.webmarket.entity.product;

import com.mj.webmarket.common.BaseTimeEntity;
import com.mj.webmarket.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private String comment;

    @Builder
    public Reply(User user, Product product, String comment){
        this.user = user;
        this.product = product;
        this.comment = comment;
    }
}
