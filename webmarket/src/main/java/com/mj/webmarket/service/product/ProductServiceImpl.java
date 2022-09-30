package com.mj.webmarket.service.product;

import com.mj.webmarket.entity.category.Category;
import com.mj.webmarket.entity.dto.product.ProductDetailResponse;
import com.mj.webmarket.entity.dto.product.ProductListResponse;
import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.exception.ProductNotFoundException;
import com.mj.webmarket.repository.category.CategoryRepository;
import com.mj.webmarket.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Product> searchProductByCondition(ProductSearchForm form, Pageable pageable) {
        if (isEmpty(form.getTitle()) && form.getCategory()==null){
            return searchAll(pageable);
        }else if(isEmpty(form.getTitle()) && form.getCategory()!=null){
            return searchByCategory(form, pageable);
        }else if (!isEmpty(form.getTitle()) && form.getCategory()==null){
            return searchByTitle(form, pageable);
        }else{
            return searchByTitleAndCategory(form, pageable);
        }
    }

    private Page<Product> searchByTitleAndCategory(ProductSearchForm form, Pageable pageable) {
        Category category = categoryRepository.findById(form.getCategory().getId()).orElseThrow(()->new ProductNotFoundException());
        return productRepository.findByTitleAndCategory(form.getTitle(), category, pageable);
    }

    private Page<Product> searchByTitle(ProductSearchForm form, Pageable pageable) {
        return productRepository.findByTitle(form.getTitle(), pageable);
    }

    private Page<Product> searchByCategory(ProductSearchForm form, Pageable pageable) {
        Category category = categoryRepository.findById(form.getCategory().getId()).orElseThrow(()->new ProductNotFoundException());
        return productRepository.findByCategory(category, pageable);
    }

    public Page<Product> searchAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> searchProductByCondition(ProductSearchForm form) {
        if (isEmpty(form.getTitle()) && form.getCategory()==null){
            return searchAll();
        }else if(isEmpty(form.getTitle()) && form.getCategory()!=null){
            return searchByCategory(form);
        }else if (!isEmpty(form.getTitle()) && form.getCategory()==null){
            return searchByTitle(form);
        }else{
            return searchByTitleAndCategory(form);
        }
    }

    @Override
    public Product findOneById(Long productId) {
        return productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("해당 번호의 상품이 존재하지 않습니다..."));
    }

    @Override
    public ProductDetailResponse toProductResponseDto(Product product) {
        ProductDetailResponse response = ProductDetailResponse.builder().id(product.getId())
                .productStatus(product.getProductStatus())
                .productImages(product.getProductImages().stream().map(p -> p.getServerFileName()).collect(Collectors.toList()))
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .heartCount(product.getHeartCount())
                .replyCount(product.getReplyCount())
                .title(product.getTitle())
                .category(product.getCategory())
                .build();
        if(response.getProductImages().isEmpty())
            response.addDefaultImage();
        return response;
    }

    @Transactional
    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    @Override
    public void addHeartCount(Product product) {
        product.addHeartCount();
    }

    @Transactional
    @Override
    public void decreaseHeartCount(Product product) {
        product.decreaseHeartCount();
    }

    @Transactional
    @Override
    public void addReplyCount(Product product) {
        product.addReplyCount();
    }

    @Transactional
    @Override
    public void decreaseReplyCount(Product product) {
        product.decreaseReplyCount();
    }

    private List<Product> searchByTitleAndCategory(ProductSearchForm form) {
        Category category = categoryRepository.findById(form.getCategory().getId()).orElseThrow(()->new ProductNotFoundException());
        return productRepository.findByTitleAndCategory(form.getTitle(), category);
    }

    private List<Product> searchByTitle(ProductSearchForm form) {
        return productRepository.findByTitle(form.getTitle());
    }

    private List<Product> searchByCategory(ProductSearchForm form) {
        Category category = categoryRepository.findById(form.getCategory().getId()).orElseThrow(()->new ProductNotFoundException());
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

    public Page<ProductListResponse> productToProductListResponseDtoPage(Pageable pageable, List<Product> products) {

        List<ProductListResponse> productList = products.stream().map(p -> ProductListResponse.builder().id(p.getId()).productStatus(p.getProductStatus())
                .heartCount(p.getHeartCount()).replyCount(p.getReplyCount()).price(p.getPrice()).title(p.getTitle()).thumbnailImage(p.getProductImages().size() == 0 ? "/images/chicken.jpeg" : p.getProductImages().get(0).getServerFileName()).build()).collect(Collectors.toList());
        log.info("productList size {}", productList.size());
        int start = (int) pageable.getOffset();
        log.info("start {} ", start);
        int end = Math.min((start + pageable.getPageSize()), productList.size());
        log.info("end {} ", end);
        return new PageImpl<>(productList.subList(start, end), pageable, productList.size());
    }


}
