package com.example.calculator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class CalculatorControllerTest {

  @Autowired
  private RestTestClient client;

  // Success cases

  @Test
  public void integerAdditionTest(){
    client.post().uri("/calculator/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"numbers\": [1, 2, 3, 4, 5]}")
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").isEqualTo(15.0)
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.errorMessage").doesNotExist();
  }

  @Test
  public void decimalAdditionTest(){
    client.post().uri("/calculator/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"numbers\": [1.5, 2.5, 3.0]}")
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").isEqualTo(7.0)
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.errorMessage").doesNotExist();
  }

  @Test
  public void negativeNumberAdditionTest(){
    client.post().uri("/calculator/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"numbers\": [-1, -2, -3]}")
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").isEqualTo(-6.0)
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.errorMessage").doesNotExist();
  }

  // Failure cases

  @Test
  public void listWithNullsAdditionTest(){
    client.post().uri("/calculator/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"numbers\": [1, null, 3]}")
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.result").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.errorMessage").isEqualTo("numbers list must not contain null elements");
  }

  @Test
  public void emptyListAdditionTest(){
    client.post().uri("/calculator/add")
            .contentType(MediaType.APPLICATION_JSON)
            .body("{\"numbers\": []}")
            .exchange()
            .expectBody()
            .jsonPath("$.result").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.errorMessage").isEqualTo("numbers list must not be empty");
  }
}
