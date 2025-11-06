package com.biz.store.orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.biz.store.customers.CustomerDto;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;    
    @Autowired
    private WebClient webClient;

    public void createOrder(OrderDto dto){

        //validate customer on orders
        CustomerDto[] customers = webClient.get()
                .uri("api/customers",
                        uriBuilder -> uriBuilder.queryParam("email", dto.getCustomerEmail()).build())
                .retrieve()
                .bodyToMono(CustomerDto[].class)
                .block();

        if(customers == null || customers.length != 1){
            throw new IllegalArgumentException("Customer not found. Invalid email provided.");
        }

        //validate products on orders

        //process each line item

        //calculate subtotals

        //calculate total

        List<OrderLineItem> items = dto.getOrderLineItems()
            .stream()
            .map(this::mapToOrderLineItem)
            .toList();

        Order order = Order.builder()
            .orderNumber(UUID.randomUUID().toString())
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
            .orderLineItems(
                order.getOrderLineItems()
                .stream()
                .map(this::mapToOrderLineItemDto)
                .toList())
            .build();
    }

    private OrderLineItemDto mapToOrderLineItemDto(OrderLineItem item){
        return OrderLineItemDto.builder()
            .code(item.getCode())
            .price(item.getPrice())
            .quantity(item.getQuantity())
            .build();
    }

}
