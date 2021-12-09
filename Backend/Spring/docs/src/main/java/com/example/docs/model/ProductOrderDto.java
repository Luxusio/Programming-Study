package com.example.docs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@Getter
public class ProductOrderDto {
    private final ProductDto product;
    private final Long eachPrice;
    private final Long amount;
}
