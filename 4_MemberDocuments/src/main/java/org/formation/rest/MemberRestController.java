package org.formation.rest;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.formation.rest.views.RestViews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/members")
public class MemberRestController {

	@Autowired
	MemberRepository memberRepository;
	
	Logger logger = LoggerFactory.getLogger(MemberRestController.class);


	@GetMapping
	@JsonView(RestViews.List.class)
	public List<Member> findAll(@RequestParam(required = false) String q) throws InterruptedException {
		logger.info("/members");
		Thread.sleep(5000);
		if (ObjectUtils.isEmpty(q))
			return memberRepository.findAll();
		return memberRepository.findByNomContainsOrPrenomContainsAllIgnoreCase(q, q);
	}

	@GetMapping("/{id}")
	@JsonView(RestViews.Detail.class)
	public Member findOne(@PathVariable long id) {
		return memberRepository.fullLoad(id).orElseThrow(() -> new EntityNotFoundException("No such member " + id));
	}

	@PostMapping
	public ResponseEntity<Member> insertOne(@Valid @RequestBody Member member) {
		if (member.getId() > 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must not provide and id");
		}

		return new ResponseEntity<Member>(memberRepository.save(member), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Member> replaceOne(@Valid @RequestBody Member member) {
		memberRepository.findById(member.getId())
				.orElseThrow(() -> new EntityNotFoundException("Id " + member.getId()));

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberRepository.save(member));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Member> mergeMember(@PathVariable long id, @RequestBody Member member) {
		Member originalMember = memberRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Id " + id));

		member = originalMember.merge(member);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberRepository.save(member));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOne(@PathVariable long id) {
		memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id " + id));
		memberRepository.deleteById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
