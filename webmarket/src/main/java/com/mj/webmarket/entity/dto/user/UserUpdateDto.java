package com.mj.webmarket.entity.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {

    private String nickname;
    private MultipartFile userImage;

    @Builder
    public UserUpdateDto(String nickname, MultipartFile userImage) {
        this.nickname = nickname;
        this.userImage = userImage;
    }
}
