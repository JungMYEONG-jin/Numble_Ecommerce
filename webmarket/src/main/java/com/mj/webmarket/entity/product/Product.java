package com.mj.webmarket.entity.product;

import com.mj.webmarket.common.BaseTimeEntity;
import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.heart.Heart;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<Heart> hearts = new ArrayList<>();
    private Integer price;
}
