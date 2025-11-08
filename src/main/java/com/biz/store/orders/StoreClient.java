package com.biz.store.orders;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

import com.biz.store.customers.CustomerDto;
import com.biz.store.products.ProductDto;

public interface StoreClient {

    @GetExchange("/api/customers")
    CustomerDto[] getCustomers(@RequestParam(name = "email", required = false) String email);    

    @GetExchange("/api/products")
    ProductDto[] getProducts(@RequestParam(name = "codes", required = false) List<String> codes);

    @PutExchange("/api/products")
    void updateProducts(@RequestBody ProductDto[] productDtos);

}
