package org.formation.rest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.formation.model.Document;
import org.formation.model.DocumentRepository;
import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/members/{idMember}/documents")
public class DocumentRestController {

	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	MemberRepository memberRepository;

	@GetMapping
	public List<Document> findAllDocumentsOfOneMember(@PathVariable Long idMember) {
		return documentRepository.findByOwnersId(idMember);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Document> addDocument(@PathVariable Long idMember, @RequestBody @Valid Document doc) {
		Member m = memberRepository.findById(idMember).orElseThrow(() -> new EntityNotFoundException("No such id " + idMember));
		Date d = new Date();
		doc.setUploadedDate(d);
		m.addDocument(doc);
		m = memberRepository.save(m);
		Optional<Document> ret = m.getDocuments().stream().filter(it -> it.getUploadedDate().equals(d)).findFirst();
		
		if ( ret.isEmpty() ) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Document>(ret.get(),HttpStatus.CREATED);
		
	}

}
