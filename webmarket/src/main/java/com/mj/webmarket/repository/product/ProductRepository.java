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

    // category 검색 + 페이징
    Page<Product> findByCategory(Pageable pageable, Category category);

    Optional<Product> findByCategory(Category category);
}
