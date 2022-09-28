package com.mj.webmarket.entity.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchForm {
    private Long categoryId; // category
    private String title; // product name
}
