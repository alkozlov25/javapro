package edu.t1.mapper;

import edu.t1.dto.UserLimitDto;
import edu.t1.entity.UserLimit;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserLimitMapper {
    public static UserLimitDto toDto(UserLimit limit) {
        UserLimitDto dto = new UserLimitDto();
        dto.setUserid(limit.getUserid());
        dto.setDefLimit(limit.getDefLimit());
        dto.setCurrLimit(limit.getCurrLimit());
        dto.setOperations(Optional.ofNullable(limit.getOperations())
                .orElse(Collections.emptyList())
                .stream()
                .map(OperationsMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public static UserLimit toEntity(UserLimitDto dto) {
        UserLimit limit = new UserLimit();
        limit.setUserid(dto.getUserid());
        limit.setDefLimit(dto.getDefLimit());
        limit.setCurrLimit(dto.getCurrLimit());
        limit.setOperations(Optional.ofNullable(dto.getOperations())
                .orElse(Collections.emptyList())
                .stream()
                .map(OperationsMapper::toEntity)
                .collect(Collectors.toList()));
        return limit;
    }
}
