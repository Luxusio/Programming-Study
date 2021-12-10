package com.example.docs.controller;

import com.example.docs.model.*;
import com.example.docs.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureRestDocs
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @Test
    void test_GET() throws Exception {
        // given
        Mockito.when(orderService.search(any()))
                .thenReturn(new OrderSearchResult(List.of(
                        new OrderDto(1L, List.of(
                                new ProductOrderDto(
                                        new ProductDto(1L, "사과", new MemberDto(2L, "사과농장")),
                                        1000L,
                                        10L),
                                new ProductOrderDto(
                                        new ProductDto(2L, "바나나", new MemberDto(3L, "바나나농장")),
                                        2000L,
                                        5L)
                        ), new MemberDto(1L, "김경재"),
                                18000L,
                                0.2,
                                false),
                        new OrderDto(2L, List.of(
                                new ProductOrderDto(
                                        new ProductDto(3L, "맥북 프로", new MemberDto(4L, "애플")),
                                        10000000L,
                                        1L)
                        ), new MemberDto(2L, "프로 개발자"),
                                10000000L,
                                null,
                                true)), 2L));

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("offset", "0");
        info.add("limit", "50");

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/orders")
                .params(info)
                .accept(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk())
                .andDo(document("orders-get",
                        requestParameters(
                                parameterWithName("offset").description("Paging offset (0 < offset)"),
                                parameterWithName("limit").description("Paging limit (0 < limit)"),
                                parameterWithName("customerKey").optional().description("Customer key, multiple allowed"),
                                parameterWithName("productKey").optional().description("Product key, multiple allowed"),
                                parameterWithName("minTotalPrice").optional().description("min total price"),
                                parameterWithName("maxTotalPrice").optional().description("max total price")),
                        responseFields(
                                fieldWithPath("results[].key").type(JsonFieldType.NUMBER).description("order key"),
                                fieldWithPath("results[].productOrders[].product.key").type(JsonFieldType.NUMBER).description("product key"),
                                fieldWithPath("results[].productOrders[].product.name").type(JsonFieldType.STRING).description("product name"),
                                fieldWithPath("results[].productOrders[].product.seller.key").type(JsonFieldType.NUMBER).description("seller member key"),
                                fieldWithPath("results[].productOrders[].product.seller.name").type(JsonFieldType.STRING).description("seller name"),
                                fieldWithPath("results[].productOrders[].eachPrice").type(JsonFieldType.NUMBER).description("price of each product"),
                                fieldWithPath("results[].productOrders[].amount").type(JsonFieldType.NUMBER).description("amount of ordered product"),
                                fieldWithPath("results[].customer.key").type(JsonFieldType.NUMBER).description("customer member key"),
                                fieldWithPath("results[].customer.name").type(JsonFieldType.STRING).description("customer name"),
                                fieldWithPath("results[].totalPrice").type(JsonFieldType.NUMBER).description("total order price (discounted)"),
                                fieldWithPath("results[].discountRate").type(JsonFieldType.NUMBER).optional().description("total price discount rate"),
                                fieldWithPath("results[].canceled").type(JsonFieldType.BOOLEAN).description("order canceled"),
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("total count of search result")
                        )));

        verify(orderService, times(1))
                .search(any());
    }

    @Test
    void test_GET_ex() throws Exception {
        // given

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("offset", "");
        info.add("limit", "");

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/orders")
                .params(info)
                .accept(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().is4xxClientError())
                .andDo(document("common-ex",
                        responseFields(
                                fieldWithPath("errorMessage").type(JsonFieldType.STRING).description("error message")
                        )));

        verify(orderService, times(0))
                .search(any());
    }

}