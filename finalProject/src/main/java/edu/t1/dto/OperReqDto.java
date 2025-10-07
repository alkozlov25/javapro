package edu.t1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OperReqDto {
    @NotNull
    private Long userid;

    @NotNull
    private Double amount;

    @NotNull
    private LocalDate date;
}