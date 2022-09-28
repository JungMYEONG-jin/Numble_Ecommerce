package com.mj.webmarket.entity.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 사용자 정보 return 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String nickName;
    private String profileImage;
    private List<Long> heartProducts;

    @Builder
    public UserResponseDto(Long userId, String nickName, String profileImage, List<Long> heartProducts) {
        this.userId = userId;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.heartProducts = heartProducts;
    }
}
