package com.example.docs.controller;

import com.example.docs.model.ErrorDto;
import com.example.docs.model.OrderSearchQuery;
import com.example.docs.model.OrderSearchResult;
import com.example.docs.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("")
    public OrderSearchResult search(OrderSearchQuery query) {

        if (query.getOffset() == null || query.getLimit() == null) {
            throw new IllegalArgumentException("offset과 Limit는 필수입니다.");
        }

        if (query.getOffset() < 0) {
            throw new IllegalArgumentException("offset은 양수여야 합니다.");
        }

        return orderService.search(query);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorDto illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ErrorDto(e.getMessage());
    }

}
