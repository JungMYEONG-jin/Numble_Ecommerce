package com.mj.webmarket.entity;

import com.mj.webmarket.common.BaseTimeEntity;
import com.mj.webmarket.entity.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
//    @NotBlank(message = "이메일을 제대로 입력해주세요.")
//    @Email
    private String email;
//    @NotBlank(message = "비밀번호에 공백은 허용되지 않습니다..")
    private String password;
//    @NotNull(message = "닉네임을 입력해주세요..")
    private String nickname;
//    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;
    private Integer tradeCount;
    private String birth; // 19990909
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
        product.setUser(this);
    }
}
