package com.example.paymentcore.service;

import com.example.paymentcore.exception.NoMoneyException;
import com.example.paymentcore.exception.NotFoundException;
import com.example.paymentcore.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final RestClient restClient;

    public List<Product> findProductsByUserId(Integer userId) {
        return restClient
                .get()
                .uri("/user?userid={id}", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    throw new NotFoundException(resp.getStatusText());
                })
                .body(new ParameterizedTypeReference<>() {});
    }

    public Product findById(Integer id) {
        return restClient
                .get()
                .uri("/user?id={id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    throw new NotFoundException(resp.getStatusText());
                })
                .body(Product.class);
    }

    public ResponseEntity<Product> pay(Integer productId, Double amount) {
        return restClient
                .get()
                .uri("/pay?id={id}&amount={amount}", productId, amount)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    throw new NotFoundException(resp.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, resp) -> {
                    throw new NoMoneyException(resp.getStatusText());
                })
                .toEntity(Product.class);
                //.body(Product.class);

    }
}
