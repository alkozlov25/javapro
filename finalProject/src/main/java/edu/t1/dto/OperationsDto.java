package edu.t1.dto;

import edu.t1.enums.OperState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OperationsDto {
    private Long id;

    @NotNull
    private Double amount;

    @NotNull
    private LocalDate date;

    private OperState state;
}