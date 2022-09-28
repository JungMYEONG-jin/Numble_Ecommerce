package com.mj.webmarket.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    음식(1),가구(2),전자기기(3);
    private final Integer id;
}
