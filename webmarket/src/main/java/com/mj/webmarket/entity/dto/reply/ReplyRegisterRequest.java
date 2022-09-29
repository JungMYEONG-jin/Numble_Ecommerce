package com.mj.webmarket.entity.dto.reply;

import com.mj.webmarket.entity.product.Product;
import com.mj.webmarket.entity.product.Reply;
import com.mj.webmarket.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyRegisterRequest {
    private String comment;

    public ReplyRegisterRequest(String comment) {
        this.comment = comment;
    }

    public Reply toReply(User user, Product product){
        return Reply.builder().comment(comment).user(user).product(product).build();
    }

}
