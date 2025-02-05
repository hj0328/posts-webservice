package com.hj.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void profile은_인증없이_호출가능() throws Exception {
        // given
        String expected = "default";

        // then
        ResponseEntity<String> response = restTemplate.getForEntity("/profile", String.class);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody())
                .isEqualTo(expected);
    }
}