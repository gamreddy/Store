package com.biz.store.products;

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
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Products is working!");
    }	
	
    @GetMapping
    public List<Product> getProducts(@RequestParam(name = "code", required = false) String code) {
    	if(code == null || code.isBlank()) {
    		return productRepository.findAll();
    	}else {
            return productRepository.findByCode(code);    		
    	}
    	
    }    
    
    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable("id") long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Event with id " + productId + " not found"));
    }    

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") long productId) {
    	productRepository.deleteById(productId);
    }    

//    @GetMapping(path = "/productByCode")
//    public List<Product> getProductByCode(@RequestParam("code") String code) {
//        return productRepository.findByCode(code)
//                .orElseThrow(() -> new NoSuchElementException("Event with id " + code + " not found"));
//    }        
//    
    @PostMapping
    public Product create(@RequestBody Product product) {        
        return productRepository.save(product);
    }    	
	
}
