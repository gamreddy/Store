package com.biz.store.customers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;	
	
	public void createCustomer(CustomerDto customerDto) {
		Customer customer = Customer.builder()
				.name(customerDto.getName())
				.email(customerDto.getEmail())
				.address(customerDto.getAddress())
				.build();
		customerRepository.save(customer);
	}
	
	public List<CustomerDto> fetchAllCustomers(){
		List<Customer> customers = customerRepository.findAll();
		return customers.stream().map(customer -> mapToDto(customer)).toList();
	}
	
	public CustomerDto findCustomerById(Long id) {	
		Optional<Customer> customer = customerRepository.findById(id);
		if(customer.isEmpty()) {
			return null;
		}else {
			return mapToDto(customer.get());
		}	
	}
	
	public List<CustomerDto> findCustomerByEmail(String email) {
		List<Customer> customers =customerRepository.findByEmail(email);
		return customers.stream().map(customer -> mapToDto(customer)).toList();
	}	
	
	public void delete(long id) {
		customerRepository.deleteById(id);
	}	
	
	private CustomerDto mapToDto(Customer customer) {
		return CustomerDto.builder()
				.id(customer.getId())
				.name(customer.getName())
				.email(customer.getEmail())
				.address(customer.getAddress())
				.build();
	}
}
