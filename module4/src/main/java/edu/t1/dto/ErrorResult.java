package edu.t1.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResult {
    private String httpCode;
    private String errMsg;
}
