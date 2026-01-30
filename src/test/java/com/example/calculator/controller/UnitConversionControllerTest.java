package com.example.calculator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestTestClient
public class UnitConversionControllerTest {
  //Success cases

  @Autowired
  private RestTestClient client;

  private boolean closeTo(Object actualValueObject, double expectedValue, double delta) {
    if (!(actualValueObject instanceof Number actualNumber)) {
      return false;
    }
    BigDecimal actualValueBd = BigDecimal.valueOf(actualNumber.doubleValue());
    BigDecimal expectedValueBd = BigDecimal.valueOf(expectedValue);
    System.out.println("Actual value: " + actualValueBd);
    System.out.println("Expected value: " + expectedValueBd);
    return actualValueBd.subtract(expectedValueBd).abs()
            .compareTo(BigDecimal.valueOf(delta)) <= 0;
  }

  // Test conversion of 50 km to miles
  @Test
  public void convert50KmToMiles(){
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "value": 50,
                  "fromUnitId": 5,
                  "toUnitId": 6
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").value(result ->
                assertTrue(closeTo(result, 31.06856, 0.0001))
            )
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
  }
  // Test conversion of 50 miles to km
  @Test
  public void convert50MilesToKm(){
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "value": 50,
                  "fromUnitId": 6,
                  "toUnitId": 5
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").value(result ->
                assertTrue(closeTo(result, 80.4672, 0.0001))
            )
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
  }
  // Test conversion of -20 celsius to fahrenheit
  @Test
  public void convertNeg20CelsiusToFahrenheit(){
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "value": -20,
                  "fromUnitId": 2,
                  "toUnitId": 3
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").value(result ->
                assertTrue(closeTo(result, -4, 0.0001))
            )
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
  }
  // Test conversion from base unit meters to itself
  @Test
  public void convertMetersToMeters(){
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "value": 100,
                  "fromUnitId": 4,
                  "toUnitId": 4
                }
                """)
            .exchangeSuccessfully()
            .expectBody()
            .jsonPath("$.result").value(result ->
                assertTrue(closeTo(result, 100, 0.0001))
            )
            .jsonPath("$.errorMessage").isEqualTo(null)
            .jsonPath("$.success").isEqualTo(true);
  }

  //Failure cases

  // Test conversion of 30 Kelvin to meters
  @Test
  public void convert30KelvinToMeters(){
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "value": 30,
                  "fromUnitId": 1,
                  "toUnitId": 4
                }
                """)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.result").isEqualTo(null)
            .jsonPath("$.errorMessage").isEqualTo("Units are of different categories.");
  }
  // Test conversion of invalid unit ID
  @Test
  public void convertWithInvalidUnitId(){
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "value": 100,
                  "fromUnitId": 999,
                  "toUnitId": 4
                }
                """)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.result").isEqualTo(null)
            .jsonPath("$.errorMessage").isEqualTo("From unit not found: 999")
            .jsonPath("$.success").isEqualTo(false);
  }
  // Test conversion with null value
  @Test
  public void convertWithNullValue(){
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                {
                  "value": null,
                  "fromUnitId": 5,
                  "toUnitId": 6
                }
                """)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.result").isEqualTo(null)
            .jsonPath("$.errorMessage").isEqualTo("Value cannot be null.")
            .jsonPath("$.success").isEqualTo(false);
  }
  // Test conversion with non-numerical value - will fail JSON parsing before it reaches the
  // controller
  @Test
  public void convertWithNonNumericalValue() {
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
          {
            "value": "abc",
            "fromUnitId": 5,
            "toUnitId": 6
          }
          """)
            .exchange()
            .expectStatus().isBadRequest();
  }
  // Test conversion of incoming data with missing fields - will fail JSON parsing before it
  // reaches the controller
  @Test
  public void convertWithMissingFields() {
    client.post().uri("/unitConversion/convertUnitById")
            .contentType(MediaType.APPLICATION_JSON)
            .body("""
                    {
                      "fromUnitId": 5,
                      "toUnitId": 6
                      }
                    """)
            .exchange()
            .expectStatus().isBadRequest();
  }
}
