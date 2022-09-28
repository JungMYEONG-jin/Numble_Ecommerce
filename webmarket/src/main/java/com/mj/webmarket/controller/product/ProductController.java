package com.mj.webmarket.controller.product;

import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.repository.product.ProductRepository;
import com.mj.webmarket.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private ProductSearchForm form = new ProductSearchForm();

    /**
     * 전체 상품목록
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/products")
    public String productList(Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable){
        Page<Product> products = productService.searchProductByCondition(form, pageable);
        model.addAttribute("products", products);
        model.addAttribute("searchForm", form);
        return "products/productList";
    }

    /**
     * 카테고리, 상품이름 검색
     * @param model
     * @param searchForm
     * @param pageable
     * @return
     */
    @PostMapping("/products")
    public String productListPost(Model model, @ModelAttribute("searchForm") ProductSearchForm searchForm, @PageableDefault(page=0, size = 10, direction = Sort.Direction.DESC) Pageable pageable)
    {
        this.form = searchForm;
        log.info("cateid {}, title {}", form.getCategoryId(), form.getTitle());
        Page<Product> products = productService.searchProductByCondition(form, pageable);
        model.addAttribute("products", products);
        return "products/productList";
    }



}
