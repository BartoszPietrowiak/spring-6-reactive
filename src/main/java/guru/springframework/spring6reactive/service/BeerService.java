package guru.springframework.spring6reactive.service;

import guru.springframework.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(Integer beerId);

    Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO);

    Mono<BeerDTO> pathBeer(Integer beerId, BeerDTO beerDTO);

    Mono<Void> deleteBeer(Integer beerId);
}
