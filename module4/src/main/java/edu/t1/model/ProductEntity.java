package edu.t1.model;

import edu.t1.enums.ProductType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
