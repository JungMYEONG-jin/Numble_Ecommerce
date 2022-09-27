package com.mj.webmarket.entity.product;

import com.mj.webmarket.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ProductImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private String filePath;
    private String serverFileName;
    private String originalFileName;

    @Builder
    public ProductImage(Product product, String filePath, String serverFileName, String originalFileName){
        this.product = product;
        this.filePath = filePath;
        this.serverFileName = serverFileName;
        this.originalFileName = originalFileName;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
