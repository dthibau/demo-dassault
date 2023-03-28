package org.formation.repository;

import java.util.Arrays;

import org.formation.model.Customer;
import org.formation.model.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.ApplicationContext;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
public class CustomerRepositoryTest {
	
	@Autowired
	CustomerRepository customerRepository;

	@BeforeEach
	public void initializeMongo(@Autowired ApplicationContext context) {
		
		Arrays.asList(context.getBeanDefinitionNames()).stream().forEach(System.out::println);
		
		customerRepository.deleteAll().block();
		
		Flux<Customer> customers = Flux.just(
		Customer.builder().firstName("David").lastName("THIBAU").build(),
		Customer.builder().firstName("Léopold").lastName("THIBAU").build(),
		Customer.builder().firstName("Antoine").lastName("THIBAU").build(),
		Customer.builder().firstName("Myrna").lastName("THIBAU").build(),
		Customer.builder().firstName("Myrlande").lastName("JEAN PIERRE").build());
		
		customerRepository.insert(customers).blockLast();
	}
	
	@Test
	public void testByLastName() {
		
		StepVerifier.create(customerRepository.findByLastName("THIBAU"))
		             .expectNextCount(4)
		             .expectComplete()
		             .verify();

	}
	
	@Test
	public void testContainingQ() {
		
		StepVerifier.create(customerRepository.findByLastNameContainsOrFirstNameContainsAllIgnoreCase("myr","myr"))
		             .expectNextCount(2)
		             .expectComplete()
		             .verify();

	}
}
