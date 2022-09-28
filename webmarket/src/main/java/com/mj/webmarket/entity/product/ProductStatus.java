package com.mj.webmarket.entity.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    TRADING("거래가능"),
    RESERVED("예약중"),
    FINISHED("거래완료");
    private String value;
}
