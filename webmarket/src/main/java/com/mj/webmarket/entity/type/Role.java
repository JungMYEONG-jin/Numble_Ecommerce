package com.mj.webmarket.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("운영자"),
    USER("일반유저"),
    MANAGER("매니저");
    private String value;
}
