package com.example.docs.service;

import com.example.docs.model.OrderSearchQuery;
import com.example.docs.model.OrderSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {

    // to mock
    public OrderSearchResult search(OrderSearchQuery query) {
        return new OrderSearchResult(new ArrayList<>(), 0L);
    }

}
