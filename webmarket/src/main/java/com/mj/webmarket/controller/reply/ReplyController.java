package com.mj.webmarket.controller.reply;

import com.mj.webmarket.entity.dto.reply.ReplyListResponse;
import com.mj.webmarket.entity.dto.reply.ReplyRegisterRequest;
import com.mj.webmarket.entity.dto.user.UserResponseDto;
import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.Reply;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.service.product.ProductServiceImpl;
import com.mj.webmarket.service.product.ReplyServiceImpl;
import com.mj.webmarket.service.user.UserServiceImpl;
import lombok.Getter;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyServiceImpl replyService;
    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;

    /**
     * 상품에 달린 댓글 보기
     * @param productId
     * @param userDetails
     * @param model
     * @return
     */
    @GetMapping("/products/{productId}/reply")
    public String showReply(@PathVariable("productId") Long productId, @AuthenticationPrincipal UserDetails userDetails, Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable){

        List<Reply> replyListResponses = replyService.showProductReviewsWithoutPaging(productId);
        Page<ReplyListResponse> replies = replyService.productToProductListResponseDtoPage(pageable, replyListResponses);
        Product findedProduct = productService.findOneById(productId);
        model.addAttribute("replies", replies);
        model.addAttribute("pageInfo", findedProduct.getId());
        return "reply/replyList";
    }

    @GetMapping("/products/{productId}/reply/register")
    public String registerReply(@PathVariable("productId") Long productId, @AuthenticationPrincipal UserDetails userDetails, Model model){
        model.addAttribute("pageInfo", productId);
        return "reply/registerForm";
    }

    @PostMapping("/products/{productId}/reply/register")
    public String registerReply(@PathVariable("productId") Long productId, @AuthenticationPrincipal UserDetails userDetails, @Validated ReplyRegisterRequest request){
        Product product = productService.findOneById(productId);
        User user = userService.findUser(userDetails.getUsername());
        Reply reply = request.toReply(user, product);
        replyService.saveReview(reply);
        return "redirect:/products/"+productId+"/reply";
    }
}
