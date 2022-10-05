package com.mj.webmarket.entity.dto.product;

import com.mj.webmarket.entity.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateRequest {
    @NotBlank(message = "상품 이름은 필수입니다.")
    private String title;
    @NotNull(message = "가격 입력은 필수입니다.")
    private int price;
    @NotBlank(message = "상품 설명은 필수입니다.")
    private String description;

    private Category category;
    List<MultipartFile> productImages;

    @Builder
    public ProductUpdateRequest(String title, int price, String description, Category category, List<MultipartFile> productImages) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.productImages = productImages;
    }

}
