package org.formation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class WebClientTest {

	WebClient webClient;

	@BeforeEach
	public void createClient() {
		webClient = WebClient.builder().baseUrl("http://localhost:8080")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}

	@Test
	public void loadOneMember() {
		Mono<User> monoUser = webClient.get().uri("/members/{id}", 1).retrieve().bodyToMono(User.class);

		monoUser.doOnNext(u -> {
			assertAll("heading", () -> assertEquals(1, u.getId()));
		}).block();
	}
	
	@Test
	public void loadAllMember() {
		Flux<User> fluxUser = webClient.get().uri("/members").retrieve().bodyToFlux(User.class);
		
		StepVerifier.create(fluxUser)
					.expectNextCount(6)
					.expectComplete()
					.verify();
	}
	@Test
	public void postOneMember() {
		User aUser = User.builder().age(18).email("new@dummy.com").nom("nom").prenom("prenom").password("secret").build();
		Mono<User> monoUser = webClient.post().uri("/members").bodyValue(aUser).retrieve().bodyToMono(User.class);
		
		StepVerifier.create(monoUser)
					.expectNextMatches(u-> u.getId() > 0)
					.expectComplete()
					.verify();
	}
}
