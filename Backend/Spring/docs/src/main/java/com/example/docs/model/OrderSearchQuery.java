package com.example.docs.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderSearchQuery {
    private Long offset;
    private Long limit;
    private List<Long> customerKey; // optional
    private List<Long> productKey; // optional
    private Long minTotalPrice; // optional
    private Long maxTotalPrice; // optional
}
