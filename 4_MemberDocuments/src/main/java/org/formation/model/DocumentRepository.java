package org.formation.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository  extends JpaRepository<Document, Long> {
	

	@Query("select m.documents from Member m where m.nom = :ownerName")
	List<Document> findByOwnersName(String ownerName);
	
	@Query("select m.documents from Member m where m.id = :idMember")
	List<Document> findByOwnersId(Long idMember);
}
