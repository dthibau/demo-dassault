package org.formation.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.formation.model.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DocumentRepositoryTest {

	@Autowired
	DocumentRepository documentRepository;
	
	@Test
	public void testFindByOwnersName() {
		
		// Suppose that we have data of import.sql
		
		assertAll("There are 6 documents for THIBAU's name",
				() -> assertEquals(6, documentRepository.findByOwnersName("THIBAU").size()));
	}
}
