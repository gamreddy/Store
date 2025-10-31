package com.biz.store.customers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Customers is working!");
    }		
	
    @GetMapping
    public List<Customer> getCustomers(@RequestParam(name = "email", required = false) String email) {
    	if(email == null || email.isBlank()) {
    		return customerRepository.findAll();
    	}else {
            return customerRepository.findByEmail(email);    		
    	}
    	
    }    
    
    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable("id") long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with id " + customerId + " not found"));
    }    

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") long customerId) {
    	customerRepository.deleteById(customerId);
    }    
    
    @PostMapping
    public Customer create(@RequestBody Customer customer) {        
        return customerRepository.save(customer);
    }       
}
