package com.epam.esm.controller.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class CustomErrorResponse {
    private Map<Object, String> errorMessage;
    private int errorStatusCode;
}
