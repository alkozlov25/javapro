package edu.t1.controller;


import edu.t1.dto.Product;
import edu.t1.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;

    @GetMapping("/user")
    public List<Product> findProductsByUserId(@RequestParam(value = "userid") Integer userid) {
        return productService.findProductsByUserId(userid);
    }

    @GetMapping("/get")
    public Product findById(@RequestParam(value = "id") Integer id) {
        return productService.findById(id);
    }
}
