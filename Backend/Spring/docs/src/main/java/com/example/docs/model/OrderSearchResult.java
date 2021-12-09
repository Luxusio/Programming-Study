package com.example.docs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@RequiredArgsConstructor
@Getter
public class OrderSearchResult {
    private final List<OrderDto> results;
    private final Long count;
}
