package com.biz.store.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;	
	
    @Test
    @Rollback    
    public void testSaveProduct() {
    	Product product = new Product();
    	product.setName("CHI Silk Infusion");
    	product.setCode("CHI-001");
    	product.setDescription("CHI Silk Infusion");
    	product.setPrice(BigDecimal.valueOf(29.99));
    	product.setStock(Short.valueOf("100"));
    	Product savedProduct = this.productRepository.save(product);
    	
    	assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getCode(), savedProduct.getCode());    	
    }
    
    @Test
    @Rollback      
    public void testFindByCodeProductFound() {    
    	Product product = new Product();
    	product.setName("CHI Silk Infusion");
    	product.setCode("CHI-001");
    	product.setDescription("CHI Silk Infusion");
    	product.setPrice(BigDecimal.valueOf(29.99));
    	product.setStock(Short.valueOf("100"));
    	Product savedProduct = this.productRepository.save(product);
    	
    	List<Product> foundProducts = productRepository.findByCode(product.getCode());
    	Product foundProduct = foundProducts.get(0);

        assertNotNull(foundProduct);
        assertEquals(product.getCode(), foundProduct.getCode());
        assertEquals(product.getName(), foundProduct.getName());    	
    	
    }
    
    @Test
    @Rollback
    public void testFindByCodeProductNotFound() {
    	List<Product> foundProducts = productRepository.findByCode("ABC-001");
        assertTrue(foundProducts.size() == 0);
    }    
    
    
}
