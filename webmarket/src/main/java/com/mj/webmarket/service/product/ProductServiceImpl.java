package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.exception.CategoryNotFoundException;
import com.mj.webmarket.exception.ProductNotFoundException;
import com.mj.webmarket.repository.category.CategoryRepository;
import com.mj.webmarket.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Product> searchProductByCondition(ProductSearchForm form, Pageable pageable) {
        if (isEmpty(form.getTitle()) && form.getCategoryId()==null){
            return searchAll(pageable);
        }else if(isEmpty(form.getTitle()) && form.getCategoryId()!=null){
            return searchByCategory(form, pageable);
        }else if (!isEmpty(form.getTitle()) && form.getCategoryId()==null){
            return searchByTitle(form, pageable);
        }else{
            return searchByTitleAndCategory(form, pageable);
        }
    }

    private Page<Product> searchByTitleAndCategory(ProductSearchForm form, Pageable pageable) {
        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(()->new ProductNotFoundException());
        return productRepository.findByTitleAndCategory(form.getTitle(), category, pageable);
    }

    private Page<Product> searchByTitle(ProductSearchForm form, Pageable pageable) {
        return productRepository.findByTitle(form.getTitle(), pageable);
    }

    private Page<Product> searchByCategory(ProductSearchForm form, Pageable pageable) {
        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(()->new ProductNotFoundException());
        return productRepository.findByCategory(category, pageable);
    }

    public Page<Product> searchAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> searchProductByCondition(ProductSearchForm form) {
        if (isEmpty(form.getTitle()) && form.getCategoryId()==null){
            return searchAll();
        }else if(isEmpty(form.getTitle()) && form.getCategoryId()!=null){
            return searchByCategory(form);
        }else if (!isEmpty(form.getTitle()) && form.getCategoryId()==null){
            return searchByTitle(form);
        }else{
            return searchByTitleAndCategory(form);
        }
    }

    private List<Product> searchByTitleAndCategory(ProductSearchForm form) {
        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(()->new ProductNotFoundException());
        return productRepository.findByTitleAndCategory(form.getTitle(), category);
    }

    private List<Product> searchByTitle(ProductSearchForm form) {
        return productRepository.findByTitle(form.getTitle());
    }

    private List<Product> searchByCategory(ProductSearchForm form) {
        Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(()->new ProductNotFoundException());
        return productRepository.findByCategory(category);
    }

    public List<Product> searchAll() {
        return productRepository.findAll();
    }




    private boolean isEmpty(String text){
        if(text==null || text.isEmpty())
            return true;
        return false;
    }
}
