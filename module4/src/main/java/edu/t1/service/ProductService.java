package edu.t1.service;

import edu.t1.dto.Product;
import edu.t1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findProductsByUserId(Integer userId) {
        return productRepository.findProductEntitiesByUserId(userId)
                .stream()
                .map(x -> Product.builder().id(x.getId())
                        .type(x.getType())
                        .balance(x.getBalance())
                        .account(x.getAccount())
                        .userId(x.getUserId())
                        .build())
                .collect(Collectors.toList());
    }

    public Product findById(Integer id) {

        return productRepository.findById(id)
                .map(x -> Product.builder().id(x.getId())
                        .type(x.getType())
                        .balance(x.getBalance())
                        .account(x.getAccount())
                        .userId(x.getUserId())
                        .build())
                .orElse(null);
    }
}
