package com.example.paymentcore;

import com.example.paymentcore.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PaymentCoreApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PaymentCoreApplication.class, args);

        ProductService productService = ctx.getBean(ProductService.class);

        //Product products = productService.pay(1, 1000.0);

        //System.out.println(products);
    }

}
