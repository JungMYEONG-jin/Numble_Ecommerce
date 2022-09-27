package com.mj.webmarket.entity.heart;

import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productInfo;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Heart(Long productInfo, User user, Product product){
        this.productInfo = productInfo;
        this.user = user;
        this.product = product;
    }


}
