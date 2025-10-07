package edu.t1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserLimitDto {
    private Long userid;
    @NotNull
    private Double defLimit;
    @NotNull
    private Double currLimit;

    private List<OperationsDto> operations;
}