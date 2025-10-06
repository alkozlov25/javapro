package edu.t1.service;

import edu.t1.dto.Product;
import edu.t1.exception.NoMoneyException;
import edu.t1.model.ProductEntity;
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

    public Product payOnProduct(Integer id, Double amount) {
        Product product = findById(id);
        if (product.getBalance().compareTo(amount)<0) {
            throw new NoMoneyException(String.format("Для списания с продукта %s недостаточно средаств, баланс = %s",
                    id, product.getBalance()));
        }
        product.setBalance(product.getBalance() - amount);
        productRepository.save(ProductEntity.builder()
                .account(product.getAccount())
                .balance(product.getBalance())
                .id(product.getId())
                .type(product.getType())
                .userId(product.getUserId())
                .build());
        return product;
    }

}
