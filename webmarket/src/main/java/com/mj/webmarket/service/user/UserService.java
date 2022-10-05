package com.mj.webmarket.service.user;

import com.mj.webmarket.entity.dto.user.UserResponseDto;
import com.mj.webmarket.entity.user.User;

public interface UserService {
    /**
     * 사용자의 정보를 받아 회원가입을 수행합니다.
     * @param User 사용자의 정보
     * @return 가입된 사용자 정보
     */
    User join(User User);

    /**
     * 사용자의 이메일을 통해 사용자를 조회합니다.
     * @param email 조회할 사용자 이메일
     * @return 조회된 사용자
     */
    User findUser(String email);

    User findUserById(Long userId);

    /**
     * 사용자의 닉네임을 수정합니다.
     * @param User 수정활 사용자 엔티티
     * @param newNickName 수정될 닉네임
     * @return 수정된 유저
     */
    User update(User User, String newNickName);

    /**
     * 사용자 아이디의 이메일 중복 여부를 학인하여 true / false 를 리턴합니다.
     * @param email 검증할 이메일
     * @return 중복 : true | 사용가능 : false
     */
    boolean duplicateEmailCheck(String email);

    UserResponseDto toUserResponseDto(User user);
}
