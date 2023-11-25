package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.model.BeerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(2)
    void listBeers() {
        webTestClient.get().uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(1)
    void getBeerById() {
        webTestClient.get().uri(BeerController.BEER_BY_ID_PATH, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    @Order(1)
    void getBeerByIdNotFound() {
        webTestClient.get().uri(BeerController.BEER_BY_ID_PATH, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void saveNewBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(getBeerDto()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }

    @Test
    @Order(3)
    void saveNewBeerBadData() {
        BeerDTO testBeer = getBeerDto();
        testBeer.setBeerName("");

        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void updateBeer() {
        webTestClient.put().uri(BeerController.BEER_BY_ID_PATH, 1)
                .body(Mono.just(getBeerDto()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(4)
    void updateBeerNotFound() {
        webTestClient.put().uri(BeerController.BEER_BY_ID_PATH, 999)
                .body(Mono.just(getBeerDto()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void updateBeerBadData() {
        BeerDTO testBeer = getBeerDto();
        testBeer.setBeerStyle("");
        webTestClient.put().uri(BeerController.BEER_BY_ID_PATH, 1)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(999)
    void deleteBeer() {
        webTestClient.delete().uri(BeerController.BEER_BY_ID_PATH, 1)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void deleteBeerNotFound() {
        webTestClient.delete().uri(BeerController.BEER_BY_ID_PATH, 999)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    private BeerDTO getBeerDto() {
        return BeerDTO.builder()
                .beerName("Tyskie")
                .beerStyle("IPA")
                .beerUpc("1231321")
                .price(new BigDecimal("12"))
                .quantityOnHand(12313)
                .build();
    }
}
