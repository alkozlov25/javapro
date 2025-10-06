package com.example.paymentcore.controller;


import com.example.paymentcore.exception.NoMoneyException;
import com.example.paymentcore.exception.NotFoundException;
import com.example.paymentcore.model.Product;
import com.example.paymentcore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pays")
@RequiredArgsConstructor
public class PayController {
    public final ProductService productService;

    @GetMapping("/products")
    public List<Product> findProductsByUserId(@RequestParam(value = "userid") Integer userid) {
        return productService.findProductsByUserId(userid);
    }

    @GetMapping("/product")
    public Product findById(@RequestParam(value = "id") Integer id) {
        return productService.findById(id);
    }

    @GetMapping("/pay")
    public ResponseEntity<Product> payOnProduct(@RequestParam(value = "id") Integer id, @RequestParam(value = "amount") Double amount) {
        ResponseEntity<Product> response = productService.pay(id, amount);
        System.out.println(response);
        return response;
    }

    @ExceptionHandler(NoMoneyException.class)
    public ResponseEntity<String> handleExcepion(NoMoneyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleExcepion(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
