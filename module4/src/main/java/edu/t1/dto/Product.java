package edu.t1.dto;

import edu.t1.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class Product {
    Integer id;
    String account;
    ProductType type;
    Double balance;
    Integer userId;
}
