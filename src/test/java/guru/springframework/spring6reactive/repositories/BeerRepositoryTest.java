package guru.springframework.spring6reactive.repositories;

import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveNewBeer() {
        beerRepository.save(getTestBeer()).subscribe(beer -> {
            System.out.println(beer);
        });
    }

    Beer getTestBeer() {
        return Beer.builder()
                .beerName("ZUBR")
                .beerStyle("IPA")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(123)
                .beerUpc("123123123")
                .build();
    }

}
