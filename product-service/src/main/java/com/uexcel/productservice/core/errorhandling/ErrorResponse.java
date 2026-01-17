package com.uexcel.productservice.core.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String detail;
    private String title;
    private String instance;
}
