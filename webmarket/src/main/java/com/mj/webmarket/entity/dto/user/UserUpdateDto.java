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
    private MultipartFile multipartFile;

    @Builder
    public UserUpdateDto(String nickname, MultipartFile multipartFile) {
        this.nickname = nickname;
        this.multipartFile = multipartFile;
    }
}
