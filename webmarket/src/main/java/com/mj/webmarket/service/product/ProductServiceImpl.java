package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.exception.CategoryNotFoundException;
import com.mj.webmarket.repository.category.CategoryRepository;
import com.mj.webmarket.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Product> searchProductByCondition(ProductSearchForm form, Pageable pageable) {
        if (isEmpty(form.getTitle()) && form.getCategoryId()==null){
            productRepository.findAll(pageable);
        }else if(isEmpty(form.getTitle()) && form.getCategoryId()!=null){
            Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(()->new CategoryNotFoundException());
            productRepository.findByCategory(category, pageable);
        }else if (!isEmpty(form.getTitle()) && form.getCategoryId()==null){
            productRepository.findByTitle(form.getTitle(), pageable);
        }else{
            Category category = categoryRepository.findById(form.getCategoryId()).orElseThrow(()->new CategoryNotFoundException());
            productRepository.findByTitleAndCategory(form.getTitle(), category, pageable);
        }
        return productRepository.findAll(pageable);
    }

    private boolean isEmpty(String text){
        if(text==null || text.isEmpty())
            return true;
        return false;
    }
}
