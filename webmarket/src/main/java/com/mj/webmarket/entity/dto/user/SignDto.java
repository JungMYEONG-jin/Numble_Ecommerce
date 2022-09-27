package com.mj.webmarket.entity.dto.user;

import com.mj.webmarket.entity.type.Role;
import com.mj.webmarket.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SignDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotBlank(message = "유효한 메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    public User toUser(){
        return User.builder().name(name).nickName(nickname).email(email).password(password).phone(phone).UserRole(Role.USER).build();
    }

    @Builder
    public SignDto(String name, String nickname, String email, String password, String phone)
    {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

}
