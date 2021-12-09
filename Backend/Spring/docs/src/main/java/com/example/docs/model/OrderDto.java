package com.example.docs.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@RequiredArgsConstructor
@Getter
public class OrderDto {
    private final Long key;
    private final List<ProductOrderDto> productOrders;
    private final MemberDto customer;
    private final Long totalPrice;
    private final Double discountRate; // optional
    private final Boolean canceled;
}
