package org.formation.service;

import org.formation.model.Customer;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {

	private final ReactiveMongoTemplate reactiveMongoTemplate;
	
	public CustomerService(ReactiveMongoTemplate reactiveMongoTemplate) {
		this.reactiveMongoTemplate = reactiveMongoTemplate;
	}
	
	public Flux<Customer> creditAllCustomers(Float amount) {
		
		return reactiveMongoTemplate.findAll(Customer.class).flatMap(c -> {
			c.amount += amount;
			return reactiveMongoTemplate.save(c);
		});
		
	}
}
