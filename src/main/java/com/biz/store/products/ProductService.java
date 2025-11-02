package com.biz.store.products;

import java.util.List;
import java.util.Optional;
import com.biz.store.products.Product;
import com.biz.store.products.ProductDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public void createProduct(ProductDto productDto) {
		Product product = Product.builder()
			.name(productDto.getName())
			.description(productDto.getName())
			.code(productDto.getCode())
			.price(productDto.getPrice())
			.stock(productDto.getStock())
			.build();
		productRepository.save(product);		
	}
	
	public List<ProductDto> fetchAllProducts(){
		List<Product> products = productRepository.findAll();
		return products.stream().map(product -> mapToDto(product)).toList();
	}
	
	public ProductDto findProductById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return null;
		}else {
			return mapToDto(product.get());
		}
	}
	
	public List<ProductDto> findProductByCode(String code) {
		List<Product> products = productRepository.findByCode(code);
		return products.stream().map(product -> mapToDto(product)).toList();
	}
	
	public void delete(long id) {
		productRepository.deleteById(id);
	}
	
	private ProductDto mapToDto(Product product) {
		return ProductDto.builder()
		.id(product.getId())
		.name(product.getName())
		.description(product.getDescription())
		.price(product.getPrice())
		.stock(product.getStock())		
		.build();		
	}
	
}
