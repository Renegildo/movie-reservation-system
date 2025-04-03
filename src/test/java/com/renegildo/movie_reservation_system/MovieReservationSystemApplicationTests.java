package com.renegildo.movie_reservation_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MovieReservationSystemApplicationTests {
	@Autowired
	private final RestTemplate restTemplate;

	MovieReservationSystemApplicationTests(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturn401WhenNotAuthenticated() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/self", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
}
