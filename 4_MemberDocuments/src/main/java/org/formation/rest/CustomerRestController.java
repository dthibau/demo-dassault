package org.formation.rest;

import org.formation.model.Customer;
import org.formation.model.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

	@Autowired
	CustomerRepository customerRepository;
	
	Logger logger = LoggerFactory.getLogger(CustomerRestController.class);
	
	@GetMapping
	Flux<Customer> findAll() {
		logger.info("Customers");
		return customerRepository.findAll();
	}
}
