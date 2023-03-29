package org.formation.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	public Optional<Member> findByEmail(String email);
	
	public Optional<Member> findByEmailAndPassword(String email, String password);
	
	public List<Member> findByNomContainsOrPrenomContainsAllIgnoreCase(String partialNom, String partialPrenom);
	
	@Query("from Member m left join fetch m.documents where m.id =:id")
	public Optional<Member> fullLoad(Long id);

}
