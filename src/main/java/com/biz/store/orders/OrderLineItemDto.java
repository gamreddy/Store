package com.biz.store.orders;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLineItemDto {
    private Long id ;
    private String code ;
    private BigDecimal price;
    private Short quantity ;
}