package com.biz.store.products;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id ;
    private String code;
    private String name ;
    private String description ;
    private BigDecimal price ;
    private Short stock;
}
