package com.khamis.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ApiResponse {
    private String massage;
    private Object data;
}
