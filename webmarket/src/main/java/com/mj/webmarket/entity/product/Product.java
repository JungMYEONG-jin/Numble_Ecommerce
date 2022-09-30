package com.mj.webmarket.entity.product;

import com.mj.webmarket.common.BaseTimeEntity;
import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.heart.Heart;
import com.mj.webmarket.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // cascade all 이므로 product만 저장하면 됨
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<ProductImage> productImages = new ArrayList<>();
    //상품 정보
//    @NotBlank(message = "상품 제목은 필수입니다.")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer replyCount = 0;
    private Integer heartCount = 0;
    private Integer price;

    @Builder
    public Product(String title, String description, Integer replyCount, Integer heartCount, Integer price, ProductStatus productStatus, User user, Category category){
        this.title = title;
        this.description = description;
        this.replyCount = replyCount;
        this.heartCount = heartCount;
        this.price = price;
        this.productStatus = productStatus;
        this.user = user;
        this.category = category;
    }

    public void addReplyCount(){
        this.replyCount++;
    }

    public void decreaseReplyCount(){
        this.replyCount--;
    }

    public void addHeartCount(){
        this.heartCount++;
    }

    public void decreaseHeartCount(){
        this.heartCount--;
    }

    public void changeStatus(ProductStatus status){
        this.productStatus = status;
    }

    public void addImage(ProductImage image){
        this.productImages.add(image);
        image.setProduct(this);
    }


}
