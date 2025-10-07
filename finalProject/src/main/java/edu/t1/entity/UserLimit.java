package edu.t1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class UserLimit {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    @NotNull
    private Double defLimit;
    @NotNull
    private Double currLimit;
    @OneToMany
    @JoinColumn(name = "userid")
    private List<Operations> operations;
}
