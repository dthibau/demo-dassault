package org.formation.rest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.formation.model.Member;
import org.formation.model.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MemberRestController.class)
public class MemberRestControllerTest {

	@MockBean
	MemberRepository memberRepository;

	@Autowired
	ApplicationContext context;

	@Autowired
	MockMvc mvc;

	Member aMember;

	@BeforeEach
	public void setUp() {
		aMember = Member.builder().id(1).prenom("David").build();

	}

	@Test
	@WithMockUser(authorities = "ROLE_USER")
	public void testGetMember() throws Exception {

		given(this.memberRepository.fullLoad(1l)).willReturn(Optional.of(aMember));
//		ResultActions result = mvc.perform(get("/members/1"));
//		MvcResult mvcResult = result.andReturn();
//		System.out.println(mvcResult.getResponse().getContentAsString());
		mvc.perform(get("/members/1")).andExpect(status().isOk()).andExpect(jsonPath("$.prenom").value("David"));
		
		verify(memberRepository).fullLoad(1l);
		
	}
}
