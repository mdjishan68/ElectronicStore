package com.Electronics.Store.dtos;

import com.Electronics.Store.validate.ImageNameValid;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {

    private String message;

    private boolean success;

    private HttpStatus status;

}
