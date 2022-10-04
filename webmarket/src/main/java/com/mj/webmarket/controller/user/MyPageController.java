package com.mj.webmarket.controller.user;

import com.mj.webmarket.entity.dto.product.ProductDetailResponse;
import com.mj.webmarket.entity.dto.user.UserResponseDto;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.ProductStatus;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.exception.ProductNotFoundException;
import com.mj.webmarket.service.product.ProductServiceImpl;
import com.mj.webmarket.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;

    @GetMapping("/mypage")
    public String goMyPage(@AuthenticationPrincipal UserDetails userDetails, Model model){

        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userInfo = userService.toUserResponseDto(user);
        model.addAttribute("userInfo", userInfo);

        return "mypage/myPage";
    }

    @GetMapping("/mypage/products/{productId}")
    public String showMySpecificProduct(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails, Model model){
        String email = userDetails.getUsername();
        Product product = productService.findOneById(productId);
        if (!email.equals(product.getUser().getEmail()))
            throw new ProductNotFoundException("해당 판매자 정보가 일치하는 상품이 없습니다..");

        // user 정보 찾기
        User user = userService.findUser(email);
        UserResponseDto userInfo = userService.toUserResponseDto(user);

        ProductDetailResponse productDetailResponse = productService.toProductResponseDto(product);
        ProductStatus productStatus = productDetailResponse.getProductStatus();

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("product", productDetailResponse);
        model.addAttribute("productStatus", productStatus);

        return "mypage/myProductDetails";
    }

}
