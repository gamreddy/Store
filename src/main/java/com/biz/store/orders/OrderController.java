package com.biz.store.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Orders is working!");
    }		

    @GetMapping
    public List<OrderDto> getOrders() {
    	//return orderRepository.findAll();    	
		return orderService.fetchAllOrders();
    }        
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder2(@RequestBody OrderDto orderDto) {
		orderService.createOrder(orderDto);
	}	
    
}
