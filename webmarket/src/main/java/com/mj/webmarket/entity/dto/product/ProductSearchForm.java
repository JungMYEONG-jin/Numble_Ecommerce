package com.mj.webmarket.entity.dto.product;

import com.mj.webmarket.entity.category.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchForm {
    private Category category; // category
    private String title; // product name
}
