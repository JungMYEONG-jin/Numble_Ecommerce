package com.mj.webmarket.controller.product;

import com.mj.webmarket.entity.dto.product.ProductListResponse;
import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.dto.user.UserResponseDto;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.service.product.ProductServiceImpl;
import com.mj.webmarket.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private ProductSearchForm form = new ProductSearchForm();

    /**
     * 전체 상품목록
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/products")
    public String productListMain(@AuthenticationPrincipal UserDetails userDetails, Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable){
        form = new ProductSearchForm(); // 초기화
        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userResponseDto = userService.toUserResponseDto(user);

        List<Product> productList = productService.searchProductByCondition(form);
        Page<ProductListResponse> products = productToProductListResponseDtoPage(pageable, productList);
        for (ProductListResponse product : products) {
            log.info("product response {}", product);
        }

        model.addAttribute("userInfo", userResponseDto);
        model.addAttribute("products", products);
        model.addAttribute("searchForm", form);
        return "products/productList";
    }


    @GetMapping("/products/search")
    public String productList(@AuthenticationPrincipal UserDetails userDetails,  Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable){
        List<Product> productList = productService.searchProductByCondition(form);
        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userResponseDto = userService.toUserResponseDto(user);
        Page<ProductListResponse> products = productToProductListResponseDtoPage(pageable, productList);
        model.addAttribute("userInfo", userResponseDto);
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
    @PostMapping("/products/search")
    public String productListPost(@AuthenticationPrincipal UserDetails userDetails, Model model, @ModelAttribute("searchForm") ProductSearchForm searchForm, @PageableDefault(page=0, size = 10, direction = Sort.Direction.DESC) Pageable pageable)
    {
        this.form = searchForm;
        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userResponseDto = userService.toUserResponseDto(user);
        model.addAttribute("userInfo", userResponseDto);
        log.info("cateid {}, title {}", form.getCategoryId(), form.getTitle());
        List<Product> productList = productService.searchProductByCondition(form);
        Page<ProductListResponse> products = productToProductListResponseDtoPage(pageable, productList);
        model.addAttribute("products", products);
        return "products/productList";
    }


    private Page<ProductListResponse> productToProductListResponseDtoPage(Pageable pageable, List<Product> products) {
        List<ProductListResponse> productList = products.stream().map(p -> ProductListResponse.builder().id(p.getId()).productStatus(p.getProductStatus())
                .heartCount(p.getHeartCount()).replyCount(p.getReplyCount()).price(p.getPrice()).title(p.getTitle()).thumbnailImage(p.getProductImages().size() == 0 ? "/images/logo.jpeg" : p.getProductImages().get(0).getServerFileName()).build()).collect(Collectors.toList());
        log.info("productList size {}", productList.size());
        int start = (int) pageable.getOffset();
        log.info("start {} ", start);
        int end = Math.min((start + pageable.getPageSize()), productList.size());
        log.info("end {} ", end);
        return new PageImpl<>(productList.subList(start, end), pageable, productList.size());
    }


}
