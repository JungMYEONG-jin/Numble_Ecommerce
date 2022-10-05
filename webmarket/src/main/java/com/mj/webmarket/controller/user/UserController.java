package com.mj.webmarket.controller.user;

import com.mj.webmarket.entity.dto.user.SignDto;
import com.mj.webmarket.entity.trade.Trade;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.exception.UserEmailDupException;
import com.mj.webmarket.service.trade.TradeServiceImpl;
import com.mj.webmarket.service.user.UserImageServiceImpl;
import com.mj.webmarket.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserImageServiceImpl userImageService;
    private final TradeServiceImpl tradeService;

    @GetMapping("/")
    public String home(){
        log.info("controller home");
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        log.info("controller get login");
        return "login";
    }

    @GetMapping("/join")
    public String join(Model model){
        log.info("controller join");
        model.addAttribute("form", new SignDto());
        return "join";
    }

    @PostMapping("/join")
//    @ResponseStatus(HttpStatus.CREATED)
    public String join(@ModelAttribute @Validated SignDto signDto){
        User user = signDto.toUser();
        if (userService.duplicateEmailCheck(user.getEmail()))
            throw new UserEmailDupException();
        User joinUser = userService.join(user);
        if (joinUser.getId()!=null){
            // 추후 구현
            userImageService.save(userImageService.init(joinUser));
            Trade trade = tradeService.toTrade(user);
            tradeService.save(trade);
        }
        log.info("redirect to home");
        return "redirect:/";
    }

}
