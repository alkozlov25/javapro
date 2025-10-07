package edu.t1.service;

import edu.t1.dto.OperReqDto;
import edu.t1.dto.OperationsDto;
import edu.t1.dto.UserLimitDto;
import edu.t1.entity.Operations;
import edu.t1.entity.UserLimit;
import edu.t1.enums.OperState;
import edu.t1.exception.LimitBadRequestException;
import edu.t1.exception.LimitNotFoundException;
import edu.t1.exception.OperationNotFoundException;
import edu.t1.mapper.OperationsMapper;
import edu.t1.mapper.UserLimitMapper;
import edu.t1.repository.OperationsRepository;
import edu.t1.repository.UserLimitRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final UserLimitRepository limitRepository;
    private final OperationsRepository operationsRepository;

    public List<UserLimitDto> getAllUserLimit() {
        return limitRepository.findAll().stream()
                .map(UserLimitMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserLimitDto getUserLimitById(Long userId) {
        UserLimit userLimit = limitRepository.findById(userId)
                .orElseGet(() -> UserLimitMapper.toEntity(createLimit(userId)));
        return UserLimitMapper.toDto(userLimit);
    }

    @Transactional
    public UserLimitDto createLimit(Long userId) {
        limitRepository.findById(userId)
                .ifPresent(x -> {throw new LimitBadRequestException(userId);});

        UserLimitDto limitDto = new UserLimitDto();
        limitDto.setOperations(new ArrayList<>());
        limitDto.setDefLimit(10000.00);
        limitDto.setCurrLimit(limitDto.getDefLimit());
        limitDto.setUserid(userId);

        UserLimit userLimit = limitRepository.save(UserLimitMapper.toEntity(limitDto));
        System.out.printf("Для пользователя %s создан новый лимит", userLimit.getUserid());
        return UserLimitMapper.toDto(userLimit);
    }

    @Transactional
    public void resetCurrLimitOnNewDay() {
        limitRepository.resetCurrLimitOnNewDay();
    }

    @Transactional
    public UserLimitDto addOperation(@NotNull OperReqDto operReqDto) {
        UserLimit userLimit = limitRepository.findById(operReqDto.getUserid())
                .orElseThrow(() -> new LimitNotFoundException(operReqDto.getUserid()));

        Double lim;
        if (operReqDto.getDate().isEqual(LocalDate.now(ZoneId.of("Europe/Moscow")))) {
            lim = userLimit.getCurrLimit();
        } else {
            //получим сумму исполненных операций на дату
            lim = userLimit.getOperations().stream()
                    .filter(x -> x.getDate().equals(operReqDto.getDate()) && x.getState() == OperState.EXECUTED)
                    .map(Operations::getAmount)
                    .mapToDouble(Double::doubleValue)
                    .sum();
            System.out.printf("Сумма операций %s на дату %s ", lim, operReqDto.getDate());

            lim = userLimit.getDefLimit() - lim;
            System.out.printf("Баланс %s на дату %s ", lim, operReqDto.getDate());
        }

        if(lim.compareTo(operReqDto.getAmount()) < 0) {
            throw new LimitBadRequestException(
                    String.format("Операция на сумму %s отклонена. Превышен лимит %s на дату %s",
                            operReqDto.getAmount(), lim, operReqDto.getDate()));
        }

        OperationsDto operationsDto = new OperationsDto();
        operationsDto.setState(OperState.EXECUTED);
        operationsDto.setAmount(operReqDto.getAmount());
        operationsDto.setDate(operReqDto.getDate());
        Operations newOper = operationsRepository.save(OperationsMapper.toEntity(operationsDto));

        userLimit.getOperations().add(newOper);
        if (operReqDto.getDate().isEqual(LocalDate.now(ZoneId.of("Europe/Moscow")))) {
            //операция в текущий день, влияет на лимит
            userLimit.setCurrLimit(userLimit.getCurrLimit() - operReqDto.getAmount());
        }
        limitRepository.save(userLimit);
        return UserLimitMapper.toDto(userLimit);
    }

    @Transactional
    public UserLimitDto addOperation(@NotNull Long userId, @NotNull Double amount) {
        UserLimit userLimit = limitRepository.findById(userId)
                .orElseThrow(() -> new LimitNotFoundException(userId));

        if(userLimit.getCurrLimit().compareTo(amount) < 0) {
            throw new LimitBadRequestException(
                    String.format("Операция на сумму %s отклонена. Превышен лимит %s", amount, userLimit.getCurrLimit()));
        }

        OperationsDto operation = new OperationsDto();
        operation.setId(null);
        operation.setState(OperState.EXECUTED);
        operation.setDate(LocalDate.now(ZoneId.of("Europe/Moscow")));
        operation.setAmount(amount);
        Operations newOper = operationsRepository.save(OperationsMapper.toEntity(operation));

        userLimit.getOperations().add(newOper);
        userLimit.setCurrLimit(userLimit.getCurrLimit() - amount);
        limitRepository.save(userLimit);
        return UserLimitMapper.toDto(userLimit);
    }

    @Transactional
    public UserLimitDto cancelOperation(@NotNull Long userId, @NotNull Long operId) {
        UserLimit userLimit = limitRepository.findById(userId)
                .orElseThrow(() -> new LimitNotFoundException(userId));

        Operations operation = Optional.ofNullable(userLimit.getOperations())
                .orElseThrow(() -> new OperationNotFoundException(String.format("У userId=%s отсутствуют операции", userId)))
                .stream().filter(x -> Objects.equals(x.getId(), operId))
                .findFirst().orElse(null);

        if (operation == null) {
            throw new OperationNotFoundException(String.format("У userId=%s отсутствует операция %s", userId, operId));
        }

        operation.setState(OperState.DELETED);
        operationsRepository.cancelOperation(operId);
        System.out.printf("Операция %s удалена ", operId);

        userLimit.setCurrLimit(userLimit.getCurrLimit() + operation.getAmount());
        limitRepository.save(userLimit);
        System.out.printf("У пользователя %s восстановлен лимит на сумму %s ", userId, operation.getAmount());
        return UserLimitMapper.toDto(userLimit);
    }
}