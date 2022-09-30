package com.mj.webmarket.service.category;

import com.mj.webmarket.entity.category.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();
    public void deleteCategory(Long categoryId);

}
