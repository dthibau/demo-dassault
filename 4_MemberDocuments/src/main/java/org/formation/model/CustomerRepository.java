package org.formation.model;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

	public Flux<Customer> findByLastName(String name);
	
	public Flux<Customer> findByLastNameContainsOrFirstNameContainsAllIgnoreCase(String partialNom, String partialPrenom);

}
