package com.mj.webmarket.controller.product;

import com.mj.webmarket.aws.RealS3Uploader;
import com.mj.webmarket.aws.S3Uploader;
import com.mj.webmarket.entity.dto.product.ProductDetailResponse;
import com.mj.webmarket.entity.dto.product.ProductListResponse;
import com.mj.webmarket.entity.dto.product.ProductRegisterRequest;
import com.mj.webmarket.entity.dto.product.ProductSearchForm;
import com.mj.webmarket.entity.dto.user.UserResponseDto;
import com.mj.webmarket.entity.heart.Heart;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.ProductImage;
import com.mj.webmarket.entity.type.CategoryType;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.service.category.CategoryServiceImpl;
import com.mj.webmarket.service.heart.HeartServiceImpl;
import com.mj.webmarket.service.product.ProductImageServiceImpl;
import com.mj.webmarket.service.product.ProductServiceImpl;
import com.mj.webmarket.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private final HeartServiceImpl heartService;
    private final CategoryServiceImpl categoryService;
    private final ProductImageServiceImpl productImageService;
//    private S3Uploader s3Uploader = new S3Uploader();
    private final RealS3Uploader s3Uploader;
    private ProductSearchForm form = new ProductSearchForm();

    /**
     * ?????? ????????????
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/products")
    public String productListMain(@AuthenticationPrincipal UserDetails userDetails, Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable){
        form = new ProductSearchForm(); // ?????????
        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userResponseDto = userService.toUserResponseDto(user);

        List<Product> productList = productService.searchProductByCondition(form);
        Page<ProductListResponse> products = productService.productToProductListResponseDtoPage(pageable, productList);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("userInfo", userResponseDto);
        model.addAttribute("products", products);
        model.addAttribute("searchForm", form);
        return "products/productList";
    }

    /**
     * ????????????, ???????????? ?????? ?????? ???????????? ??????
     * @param userDetails
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/products/search")
    public String productList(@AuthenticationPrincipal UserDetails userDetails,  Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable){
        List<Product> productList = productService.searchProductByCondition(form);
        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userResponseDto = userService.toUserResponseDto(user);
        Page<ProductListResponse> products = productService.productToProductListResponseDtoPage(pageable, productList);
        model.addAttribute("userInfo", userResponseDto);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("searchForm", form);
        return "products/productList";
    }

    /**
     * ????????????, ???????????? ??????
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
        List<Product> productList = productService.searchProductByCondition(form);
        Page<ProductListResponse> products = productService.productToProductListResponseDtoPage(pageable, productList);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/productList";
    }

    /**
     * ?????? ?????? ??????????????? ??????
     * @param productId
     * @param userDetails
     * @param model
     * @return
     */
    @GetMapping("/products/{productId}")
    public String getSpecificProduct(@PathVariable("productId") Long productId, @AuthenticationPrincipal UserDetails userDetails, Model model){
        // product to response dto
        Product findedProudct = productService.findOneById(productId);
        ProductDetailResponse productResponse = productService.toProductResponseDto(findedProudct);
        log.info("reply count {}", findedProudct.getReplyCount());
        User owner = userService.findUser(findedProudct.getUser().getEmail());
        UserResponseDto ownerInfo = userService.toUserResponseDto(owner);

        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto myInfo = userService.toUserResponseDto(user);
        model.addAttribute("myInfo", myInfo);
        model.addAttribute("ownerInfo", ownerInfo);
        model.addAttribute("product", productResponse);
        log.info("user name {}", user.getName());
        return "products/productDetails";
    }


    /**
     * ????????? ?????? ????????? ??????
     * @param productId
     * @param userDetails
     * @return
     */
    //????????? ??????
    @PostMapping("/products/{productId}/addHeart")
    @ResponseBody
    public String addHeart(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findUser(userDetails.getUsername());
        Product product = productService.findOneById(productId);
        Heart heart = Heart.builder().productInfo(productId).user(user).product(product).build();
        heartService.add(heart);
        productService.addHeartCount(product);
        return "??????????????? ?????????????????????.";
    }

    /**
     * ????????? ??????
     */
    @DeleteMapping("/products/{productId}/addHeart")
    @ResponseBody
    public String deleteHeart(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findUser(userDetails.getUsername());
        Product product = productService.findOneById(productId);
        heartService.deleteOne(productId, user.getId());
        productService.decreaseHeartCount(product);
        return "??????????????? ?????????????????????.";
    }


    // ?????? ??????
    @GetMapping("/products/register")
    public String registerProduct(@AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("form", new ProductRegisterRequest());
        model.addAttribute("categoryList", categoryService.getAllCategories());
        return "products/registerForm";
    }

    @PostMapping("/products/register")
    public String postProduct(@ModelAttribute @Validated ProductRegisterRequest form, @AuthenticationPrincipal UserDetails userDetails, Model model) throws IOException {
        User user = userService.findUser(userDetails.getUsername());
        Product product = form.toProduct(user);
        productService.addProduct(product);
        ArrayList<ProductImage> products = s3Uploader.uploadList(form.getProductImages(), "products", product);
//        ArrayList<ProductImage> products = s3Uploader.uploadList(form.getProductImages(), "/images", product);//??????
        for (ProductImage productImage : products) {
            productImageService.save(productImage);
            log.info("postProduct {}", productImage.getServerFileName());
        }
        return "redirect:/products";
    }


    // ????????? ?????? ????????? ??????
    @GetMapping("products/{productId}/memberProducts/{ownerId}")
    public String getOwnerInfo(@PathVariable Long productId, @PathVariable Long ownerId, @AuthenticationPrincipal UserDetails userDetails, Model model){
        List<ProductListResponse> productList = productService.getUserProductList(ownerId);

        User user = userService.findUser(userDetails.getUsername());
        UserResponseDto userInfo = userService.toUserResponseDto(user);
        log.info("userid {}", userInfo.getUserId());

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", productId);

        return "products/MemberProductList";
    }




}
