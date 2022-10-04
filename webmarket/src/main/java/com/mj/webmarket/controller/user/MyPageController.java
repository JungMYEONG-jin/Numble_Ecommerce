package com.mj.webmarket.controller.user;

import com.mj.webmarket.aws.S3Uploader;
import com.mj.webmarket.entity.dto.product.ProductDetailResponse;
import com.mj.webmarket.entity.dto.product.ProductListResponse;
import com.mj.webmarket.entity.dto.product.ProductStatusRequest;
import com.mj.webmarket.entity.dto.user.UserResponseDto;
import com.mj.webmarket.entity.dto.user.UserUpdateDto;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.ProductStatus;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.entity.user.UserImage;
import com.mj.webmarket.exception.ProductNotFoundException;
import com.mj.webmarket.service.heart.HeartServiceImpl;
import com.mj.webmarket.service.product.ProductServiceImpl;
import com.mj.webmarket.service.user.UserImageServiceImpl;
import com.mj.webmarket.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;
    private final HeartServiceImpl heartService;
    private final UserImageServiceImpl userImageService;
    private S3Uploader s3Uploader = new S3Uploader();
    /**
     * 내 메인 페이지
     * @param userDetails
     * @param model
     * @return
     */
    @GetMapping("/mypage")
    public String goMyPage(@AuthenticationPrincipal UserDetails userDetails, Model model){

        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userInfo = userService.toUserResponseDto(user);
        model.addAttribute("userInfo", userInfo);

        return "mypage/myPage";
    }

    /**
     * 내 상품들 보기
     * @param userDetails
     * @param model
     * @return
     */
    @GetMapping("/mypage/products")
    public String showMyProducts(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userService.findUser(userDetails.getUsername());
        List<ProductListResponse> productList = productService.getUserCanSellProductList(user.getId());
        model.addAttribute("productList", productList);
        return "mypage/myProducts";
    }

    /**
     * 판매완료 상품 보기
     * @param userDetails
     * @param model
     * @return
     */
    @GetMapping("/mypage/products/complete")
    public String showMyCompletedProducts(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userService.findUser(userDetails.getUsername());
        List<ProductListResponse> userCompletedProduct = productService.getUserCompletedProduct(user.getId());
        model.addAttribute("productList", userCompletedProduct);
        return "mypage/myCompletedProducts";
    }

    /**
     * 상태 변경
     * @param productId
     * @param userDetails
     * @param productStatusRequest
     * @return
     */
    @PutMapping("/mypage/products/{productId}/setStatus")
    @ResponseBody
    public String updateProductStatus(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails, @RequestBody ProductStatusRequest productStatusRequest){
        Product product = productService.findOneById(productId);
        productService.changeProductStatus(product, productStatusRequest.getProductStatus());
        return "success";
    }

    /**
     * 판매완료로 변경
     * @param productId
     * @param userDetails
     * @return
     */
    @GetMapping("/mypage/products/{productId}/setComplete")
    public String updateProductComplete(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails){
        Product product = productService.findOneById(productId);
        productService.changeProductStatus(product, ProductStatus.FINISHED);
        return "redirect:/mypage/products/complete";
    }

    @GetMapping("/mypage/products/{productId}/setTrading")
    public String updateProductTrading(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails){
        Product product = productService.findOneById(productId);
        productService.changeProductStatus(product, ProductStatus.TRADING);
        return "redirect:/mypage/products";
    }

    /**
     * 특정 상품 보기
     * @param productId
     * @param userDetails
     * @param model
     * @return
     */
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
        ProductStatus[] productStatuses = ProductStatus.values();

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("product", productDetailResponse);
        model.addAttribute("productStatus", productStatuses);

        return "mypage/myProductDetails";
    }


    @GetMapping("/mypage/hearts")
    public String showMyHeartProducts(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userService.findUser(userDetails.getUsername());
        List<ProductListResponse> productList = heartService.getMyHeartProducts(user.getId());
        model.addAttribute("productList", productList);
        return "mypage/myHeartProducts";
    }

    @GetMapping("/mypage/update")
    public String updateMyProfile(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userService.findUser(userDetails.getUsername());
        UserUpdateDto userUpdateDto = UserUpdateDto.builder().nickname(user.getNickname()).build();
        model.addAttribute("form", userUpdateDto);
        UserImage userImage = userImageService.findByUser(user.getId());
        log.info("original {}", userImage.getOriginalFileName());
        log.info("server {}", userImage.getServerFileName());
        return "mypage/myProfileUpdate";
    }

    @PostMapping("/mypage/update")
    public String updateMyProfilePost(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute UserUpdateDto form) throws IOException {
        User user = userService.findUser(userDetails.getUsername());
        userService.update(user, form.getNickname());

        if (!form.getUserImage().isEmpty()){
            MultipartFile multipartFile = form.getUserImage();
            UserImage userImage = userImageService.findByUser(user.getId());
            if (userImage==null){
                userImage = UserImage.builder().user(user).build();
            }
            String serverUrl = s3Uploader.upload(form.getUserImage(), "/images");
            userImageService.updateMemberImage(userImage, "/images",
                    form.getUserImage().getOriginalFilename(), serverUrl);
            log.info("original {}", userImage.getOriginalFileName());
            log.info("server {}", userImage.getServerFileName());
        }
        return "redirect:/mypage";
    }

    @DeleteMapping("mypage/products/{productId}/delete")
    @ResponseBody
    public String deleteProduct(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails){

        return "OK";
    }

}
