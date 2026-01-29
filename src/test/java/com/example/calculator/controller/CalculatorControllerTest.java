package com.example.calculator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestTestClient
public class CalculatorControllerTest {

  @Autowired
  private RestTestClient client;

  // Success cases

  @Test
  public void performAdditionOK(){
    client.post().uri("/calculator/binaryOperation")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "operandOne": 5,
                  "operandTwo": 3,
                  "operator": "ADD"
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").isEqualTo(8)
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
  }

  @Test
  public void performSubtractionOK(){
    client.post().uri("/calculator/binaryOperation")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "operandOne": 10,
                  "operandTwo": 4,
                  "operator": "SUBTRACT"
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").isEqualTo(6)
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
  }

  @Test
  public void performMultiplicationOK(){
    client.post().uri("/calculator/binaryOperation")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "operandOne": 7,
                  "operandTwo": 6,
                  "operator": "MULTIPLY"
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").isEqualTo(42)
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
}

  @Test
  public void performDivisionOK(){
    client.post().uri("/calculator/binaryOperation")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "operandOne": 20,
                  "operandTwo": 4,
                  "operator": "DIVIDE"
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").isEqualTo(5)
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
  }

  // Failure cases

  @Test
  public void performDivisionByZero(){
    client.post().uri("/calculator/binaryOperation")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "operandOne": 10,
                  "operandTwo": 0,
                  "operator": "DIVIDE"
                }
                """)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.result").isEqualTo(null)
            .jsonPath("$.errorMessage").isEqualTo("Division by zero is not allowed.")
            .jsonPath("$.success").isEqualTo(false);
  }

  @Test
  public void performInvalidOperator(){
    client.post().uri("/calculator/binaryOperation")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "operandOne": 10,
                  "operandTwo": 5,
                  "operator": "MODULO"
                }
                """)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.result").isEqualTo(null)
            .jsonPath("$.errorMessage").isEqualTo("Invalid operator: MODULO")
            .jsonPath("$.success").isEqualTo(false);
  }
}
