package guru.springframework.spring6reactive.service;

import guru.springframework.spring6reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> getCustomerById(Integer customerId);

    Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> pathCustomer(Integer customerId, CustomerDTO customerDTO);

    Mono<Void> deleteCustomer(Integer customerId);
}
