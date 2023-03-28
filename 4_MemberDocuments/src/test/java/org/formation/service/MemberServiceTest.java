package org.formation.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.formation.model.Document;
import org.formation.model.DocumentRepository;
import org.formation.model.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberServiceTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	MemberService memberService;
	
	@BeforeEach
	public void initService() {
		this.memberService = new MemberService(memberRepository);	
	}
	
	@Test
	void testImport() {
		long initialMemberCount = memberRepository.count();
		long initialDocumentCount = documentRepository.count();
		
		Document doc = Document.builder()
					.contentType("dummy")
					.name("Dummy.doc")
					.uploadedDate(new Date()).build();
		
		memberService.importDocument(doc);
		
		assertAll("Adding One Member 2 docs",
				() -> assertEquals(initialMemberCount, memberRepository.count()),
				() -> assertEquals(initialDocumentCount + initialMemberCount, documentRepository.count()));

	}
	
}
