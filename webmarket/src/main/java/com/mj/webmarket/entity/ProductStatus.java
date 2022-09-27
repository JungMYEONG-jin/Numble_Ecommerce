package com.mj.webmarket.entity;

import com.mj.webmarket.entity.type.Status;

import javax.persistence.*;

@Entity
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
}
