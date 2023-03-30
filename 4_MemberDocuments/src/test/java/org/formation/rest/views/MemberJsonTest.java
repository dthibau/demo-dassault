package org.formation.rest.views;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;

import org.formation.model.Document;
import org.formation.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.ApplicationContext;

@JsonTest
public class MemberJsonTest {

	@Autowired
	ApplicationContext context;

	@Autowired
	private JacksonTester<Member> json;

	Member aMember;
	Document doc1;

	@BeforeEach
	public void setUp() {
		
		for ( String beanName : context.getBeanDefinitionNames() ) {
			System.out.println(beanName);
		}
		aMember = Member.builder().id(1).email("dd@dd.fr").documents(new HashSet<>()).build();
		doc1 = new Document();
		doc1.setName("Toto");
		aMember.addDocument(doc1);
	}

	@Test
	public void testSerialize() throws Exception {

		assertThat(this.json.forView(RestViews.List.class).write(aMember))
		        .hasJsonPathStringValue("@.email")
				.hasEmptyJsonPathValue("@.documents")
				.extractingJsonPathStringValue("@.email").isEqualTo("dd@dd.fr");

		assertThat(this.json.forView(RestViews.Detail.class).write(aMember)).hasJsonPathArrayValue("@.documents", doc1);

	}

	@Test
	public void testDeserialize() throws Exception {
		String content = "{\"id\":\"1\",\"email\":\"dd@dd.fr\"}";
		assertThat(this.json.parse(content)).isEqualTo(aMember);

		assertThat(this.json.parseObject(content).getEmail()).isEqualTo("dd@dd.fr");
	}
}