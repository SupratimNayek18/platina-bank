package com.platinabank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response"
)
public class ErrorResponseDto {

    @Schema(
            description = "Api path of the response"
    )
    private  String apiPath;

    @Schema(
            description = "Error code of the response"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message of the response"
    )
    private String errorMessage;

    @Schema(
            description = "Time of the response"
    )
    private LocalDateTime errorTime;

}
