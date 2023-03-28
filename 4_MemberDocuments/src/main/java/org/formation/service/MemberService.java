package org.formation.service;

import org.formation.model.Document;
import org.formation.model.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	
	MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public void importDocument(Document doc) {
		memberRepository.findAll().stream().forEach(m -> {
			m.addDocument(doc);
			memberRepository.save(m);
		});
	}
}
