package com.example.docs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@Getter
public class ProductDto {
    private final Long key;
    private final String name;
    private final MemberDto seller;
}
