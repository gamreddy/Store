package com.biz.store.orders;

import java.math.BigDecimal;
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

import com.biz.store.customers.Customer;
import com.biz.store.customers.CustomerRepository;
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
	@Autowired
	CustomerRepository customerRepository;	
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
    
    public Order placeOrder(@RequestBody Order order) {
    	
    	//Validate customer
    	String email = order.getCustomerEmail();
    	if(email == null || email.isBlank()) {
    		throw new IllegalArgumentException("Customer not found. Invalid email provided.");
    	}
    	List<Customer> customers = this.customerRepository.findByEmail(email)	    	;
    	if(customers == null || customers.size() == 0) {
    		throw new IllegalArgumentException("Customer not found. No customer with provided email.");
    	}
    	
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
    	
    	//Calculate subtotal
    	BigDecimal subtotal = BigDecimal.ZERO;
    	for(OrderLineItem lineItem : lineItems) {
    		Product product = this.productRepository.findByCode(lineItem.getCode()).get(0);
    		subtotal = subtotal.add(product.getPrice().multiply(BigDecimal.valueOf(lineItem.getQuantity())));
    	}    	
    	
    	//Add 10% tax & set total
    	subtotal = subtotal.add(subtotal.multiply(BigDecimal.valueOf(0.1)));    	
    	order.setTotal(subtotal);
    	
    	return orderRepository.save(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder2(@RequestBody OrderDto orderDto) {
		orderService.createOrder(orderDto);
	}	
    
}
