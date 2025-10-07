package edu.t1.mapper;

import edu.t1.dto.OperationsDto;
import edu.t1.entity.Operations;

public class OperationsMapper {
    public static OperationsDto toDto(Operations operations) {
        OperationsDto dto = new OperationsDto();
        dto.setId(operations.getId());
        dto.setAmount(operations.getAmount());
        dto.setDate(operations.getDate());
        dto.setState(operations.getState());
        return dto;
    }

    public static Operations toEntity(OperationsDto dto) {
        Operations operations  = new Operations();
        operations.setId(dto.getId());
        operations.setAmount(dto.getAmount());
        operations.setDate(dto.getDate());
        operations.setState(dto.getState());
        return operations;
    }
}
