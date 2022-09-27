package com.mj.webmarket.entity.user;

import com.mj.webmarket.common.BaseTimeEntity;
import com.mj.webmarket.entity.heart.Heart;
import com.mj.webmarket.entity.type.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @NotBlank(message = "이메일을 제대로 입력해주세요.")
    @Email
    private String email;
    @NotBlank(message = "비밀번호에 공백은 허용되지 않습니다..")
    private String password;
    @NotBlank(message = "닉네임을 입력해주세요..")
    private String nickname;
    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;
    private Integer tradeCount;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Heart> hearts = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserImage userImage;

    @Builder
    public User(String name, String email, String password, String nickName, String phone, Role UserRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.nickname = nickName;
        this.role = UserRole;
    }

    public User encodePassword(PasswordEncoder encoder){
        this.password = encoder.encode(password);
        return this;
    }

    public User updateNickName(String nickname){
        this.nickname = nickname;
        return this;
    }

}
