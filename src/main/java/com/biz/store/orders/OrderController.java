package com.biz.store.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.biz.store.products.Product;
import com.biz.store.products.ProductRepository;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderRepository orderRepository;	
	@Autowired
	ProductRepository productRepository;
	
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Orders is working!");
    }		

    @GetMapping
    public List<Order> getOrders() {
    	return orderRepository.findAll();    	
    }        
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrder(@RequestBody Order order) {
    	
    	List<OrderLineItem> lineItems = order.getOrderLineItems();    	
    	
    	//Validate each line item
    	for(OrderLineItem lineItem : lineItems) {
    		List<Product> products =  this.productRepository.findByCode(lineItem.getCode());
    		if(products == null || products.size() == 0) {
    			throw new IllegalArgumentException("Product not found");
    		}
    		
    		//check if product is available
    		if(products.get(0).getStock() < lineItem.getQuantity()) {
    			throw new IllegalStateException("Insufficient stock");
    		}
    	}
    	
    	//Process each line item
    	for(OrderLineItem lineItem : lineItems) {
    		Product product = this.productRepository.findByCode(lineItem.getCode()).get(0);
    		//reduce stock
    		product.setStock((short) (product.getStock() - lineItem.getQuantity()));    		
    		//save product
    		productRepository.save(product);
    	}
    	
    	return orderRepository.save(order);
    }
    
}
