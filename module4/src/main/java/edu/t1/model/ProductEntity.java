package edu.t1.model;

import edu.t1.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
@Entity
public class ProductEntity {
    @Id
    private Integer id;

    @Column
    private String account;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column
    private Double balance;

    @Column(name = "UsersID")
    private Integer userId;
}
