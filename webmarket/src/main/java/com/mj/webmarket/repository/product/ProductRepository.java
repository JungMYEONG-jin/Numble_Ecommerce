package com.mj.webmarket.repository.product;

import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //삭제
    void deleteByIdAndUserId(Long productId, Long userId);

    // all
    Page<Product> findAll(Pageable pageable);
    // category 검색 + 페이징
    Page<Product> findByCategory(Category category, Pageable pageable);
    // title + paging
    Page<Product> findByTitle(String title, Pageable pageable);
    // category + title
    Page<Product> findByTitleAndCategory(String title, Category category, Pageable pageable);


    // all
    List<Product> findAll();
    // category 검색 + 페이징
    List<Product> findByCategory(Category category);
    // title + paging
    List<Product> findByTitle(String title);
    // category + title
    List<Product> findByTitleAndCategory(String title, Category category);
    // userid
    List<Product> findByUserId(Long userId);

}
