package com.biz.store.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;	
    private Product product;
    private Product savedProduct;
	
    @BeforeEach
    void setUp() {
    	product = new Product();
    	product.setName("CHI Silk Infusion");
    	product.setCode("CHI-001");
    	product.setDescription("CHI Silk Infusion");
    	product.setPrice(BigDecimal.valueOf(29.99));
    	product.setStock(Short.valueOf("100"));    	
    	
    	savedProduct = this.productRepository.save(product);
    }
    
    @Test
    @Transactional
    @Rollback    
    public void testSaveProduct() {    	
    	assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getCode(), savedProduct.getCode());    	
    }
    
    @Test
    @Transactional
    @Rollback      
    public void testFindByIdProductFound() {   
    	Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());    	
    	assertEquals(savedProduct.getId(), foundProduct.get().getId());
    }
    
    @Test
    @Transactional
    @Rollback
    public void testFindByIdProductNotFound() {
    	Optional<Product> foundProducts = productRepository.findById((long) 100);
        assertTrue(foundProducts.isEmpty());
    }        
    
    @Test
    @Transactional
    @Rollback      
    public void testFindByCodeProductFound() {       	
    	List<Product> foundProducts = productRepository.findByCode(product.getCode());
    	Product foundProduct = foundProducts.get(0);

        assertNotNull(foundProduct);
        assertEquals(product.getCode(), foundProduct.getCode());
        assertEquals(product.getName(), foundProduct.getName());    	
    	
    }
    
    @Test
    @Transactional
    @Rollback
    public void testFindByCodeProductNotFound() {
    	List<Product> foundProducts = productRepository.findByCode("ABC-001");
        assertTrue(foundProducts.size() == 0);
    }    
    
    @Test
    @Transactional
    @Rollback
    public void testDeleteById() {    	
    	assertNotNull(savedProduct);
    	assertNotNull(savedProduct.getId());
    	
        this.productRepository.deleteById(savedProduct.getId());
        
    	Optional<Product> foundProduct = productRepository.findById(product.getId());
    	assertTrue(foundProduct.isEmpty());    	
    	
    }
    
    @Test
    @Transactional
    @Rollback
    public void testFindAll() {    	
    	
    	Product product2 = new Product();
    	product2.setName("CHI Flat Iron");
    	product2.setCode("CHI-002");
    	product2.setDescription("CHI Flat Iron");
    	product2.setPrice(BigDecimal.valueOf(89.99));
    	product2.setStock(Short.valueOf("25"));      
    	
    	this.productRepository.save(product2);
    	
    	List<Product> foundProducts = productRepository.findAll();
    	
    	assertTrue(foundProducts.size() == 2);
    	
    }
    
}
