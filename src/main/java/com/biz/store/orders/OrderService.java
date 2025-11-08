package com.biz.store.orders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biz.store.customers.CustomerDto;
import com.biz.store.products.ProductDto;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;    
    @Autowired
    private StoreClient storeClient;        

    public void createOrder(OrderDto dto){

        //validate customer on orders
        CustomerDto[] customers = storeClient.getCustomers(dto.getCustomerEmail());

        if(customers == null || customers.length != 1){
            throw new IllegalArgumentException("Customer not found. Invalid email provided.");
        }

        //validate products on orders
        List<String> codes = dto.getOrderLineItems().stream().map(item->item.getCode()).toList();
        ProductDto[] products = storeClient.getProducts(codes);

        if(products == null ||  products.length != codes.size()){
            throw new IllegalArgumentException("Product not found");
        }

        //Validate each line item
        BigDecimal subtotal = BigDecimal.ZERO;
    	for(OrderLineItemDto lineItem : dto.getOrderLineItems()) {
            
            Optional<ProductDto> foundProduct = Arrays.asList(products).stream().filter(prod -> prod.getCode().equals(lineItem.getCode())).findFirst();
    		if(foundProduct.isEmpty()) {
    			throw new IllegalArgumentException("Product not found");
    		}
    		
    		//check if product is available in stock
    		if(foundProduct.get().getStock() < lineItem.getQuantity()) {
    			throw new IllegalStateException("Insufficient stock");
    		}

            //calculate subtotals
            subtotal = subtotal.add(foundProduct.get().getPrice().multiply(BigDecimal.valueOf(lineItem.getQuantity())));

            //otherwise, update stock on the product
            foundProduct.get().setStock((short) (foundProduct.get().getStock() - lineItem.getQuantity()));
    	}

        //Save products
        storeClient.updateProducts(products);
                               
        //Add 10% tax
        subtotal = subtotal.add(subtotal.multiply(BigDecimal.valueOf(0.1)));    	

        List<OrderLineItem> items = dto.getOrderLineItems()
            .stream()
            .map(this::mapToOrderLineItem)
            .toList();

        Order order = Order.builder()
            .orderNumber(UUID.randomUUID().toString())
            .total(subtotal)
            .orderLineItems(items)
            .customerEmail(dto.getCustomerEmail())            
            .build();

        orderRepository.save(order);
    }    

    private OrderLineItem mapToOrderLineItem(OrderLineItemDto dto){
        return OrderLineItem.builder()
            .code(dto.getCode())
            .price(dto.getPrice())
            .quantity(dto.getQuantity())            
            .build();
    }

    public List<OrderDto> fetchAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> mapToDto(order)).toList();
    }

    private OrderDto mapToDto(Order order){
        return OrderDto.builder()
            .id(order.getId())
            .customerEmail(order.getCustomerEmail())
            .orderNumber(order.getOrderNumber())
            .status(order.getStatus())
            .total(order.getTotal())
            .orderLineItems(
                order.getOrderLineItems()
                .stream()
                .map(this::mapToOrderLineItemDto)
                .toList())
            .build();
    }

    private OrderLineItemDto mapToOrderLineItemDto(OrderLineItem item){
        return OrderLineItemDto.builder()
            .id(item.getId())
            .code(item.getCode())
            .price(item.getPrice())
            .quantity(item.getQuantity())
            .build();
    }

}
