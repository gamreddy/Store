package com.biz.store.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;	
	
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Customers is working!");
    }		
	
    @GetMapping
    public List<CustomerDto> getCustomers(@RequestParam(name = "email", required = false) String email) {
    	if(email == null || email.isBlank()) {
    		return customerService.fetchAllCustomers();
    	}else {
            return customerService.findCustomerByEmail(email);    		
    	}
    	
    }    
    
    @GetMapping(path = "/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") long customerId) {
        return customerService.findCustomerById(customerId);
    }    

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long customerId) {
    	customerService.delete(customerId);
    }    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CustomerDto customerDto) {        
        customerService.createCustomer(customerDto);
    }       
}
