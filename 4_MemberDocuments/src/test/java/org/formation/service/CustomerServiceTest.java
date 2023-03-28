package org.formation.service;

import org.formation.model.Customer;
import org.formation.model.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
public class CustomerServiceTest {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;
	
	private CustomerService customerService;
	
	@BeforeEach
	public void init() {
		this.customerService = new CustomerService(reactiveMongoTemplate);
		
		customerRepository.deleteAll().block();
		
		Flux<Customer> customers = Flux.just(
		Customer.builder().firstName("David").lastName("THIBAU").amount(100.0f).build());
		
		customerRepository.insert(customers).blockLast();
	}
	
	@Test
	public void testCredit() {
		StepVerifier.create(customerService.creditAllCustomers(10.0f))
			.expectNextMatches(customer -> customer.amount.equals(110.0f))
			.expectComplete()
			.verify();
			
			
	}
	
	
}
