package com.biz.store.orders;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private Long id ;
    private String orderNumber ;
    private String customerEmail;
    private String status;
    private BigDecimal total;
    private List<OrderLineItemDto> orderLineItems ; 
}
