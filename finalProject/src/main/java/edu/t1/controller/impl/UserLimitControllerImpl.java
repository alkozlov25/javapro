package edu.t1.controller.impl;

import edu.t1.controller.UserLimitController;
import edu.t1.dto.OperReqDto;
import edu.t1.dto.UserLimitDto;
import edu.t1.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserLimitControllerImpl implements UserLimitController {

    private final LimitService limitService;

    @Override
    public List<UserLimitDto> getAllUserLimit() {
        return limitService.getAllUserLimit();
    }

    @Override
    public UserLimitDto getUserLimitById(Long id) {
        return limitService.getUserLimitById(id);
    }

    @Override
    public UserLimitDto addOperation(OperReqDto operReqDto) {
        return limitService.addOperation(operReqDto);
    }

    @Override
    public UserLimitDto payAmount(Long id, Double amount) {
        return limitService.addOperation(id, amount);
    }

    @Override
    public UserLimitDto createUserLimitById(Long id) {
        return limitService.createLimit(id);
    }

    @Override
    public UserLimitDto cancelOperation(Long id, Long operId) {
        return limitService.cancelOperation(id, operId);
    }

    @Override
    public void resetCurrLimitOnNewDay() {
        limitService.resetCurrLimitOnNewDay();
    }
}
