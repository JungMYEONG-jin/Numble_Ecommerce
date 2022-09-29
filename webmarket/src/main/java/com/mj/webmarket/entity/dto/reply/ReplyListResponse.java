package com.mj.webmarket.entity.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
public class ReplyListResponse {

    private String comment;
    private String memberEmail;
    private String memberProfile;

    @Builder
    public ReplyListResponse(String comment, String memberEmail, String memberProfile) {
        this.comment = comment;
        this.memberEmail = memberEmail;
        this.memberProfile = memberProfile;
    }
}
