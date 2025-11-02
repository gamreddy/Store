package com.biz.store.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	ProductService productService;	
	
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Products is working!");
    }	
	
    @GetMapping
    public List<ProductDto> getProducts(@RequestParam(name = "code", required = false) String code) {
    	if(code == null || code.isBlank()) {    		
    		return productService.fetchAllProducts();
    	}else {
            return productService.findProductByCode(code);
    	}
    	
    }    
    
    @GetMapping(path = "/{id}")
    public ProductDto getProductById(@PathVariable("id") long productId) {
        return productService.findProductById(productId);
    }    

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long productId) {
    	productService.delete(productId);
    }    
   
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ProductDto productDto) {        
        productService.createProduct(productDto);
    }    	
	
}
