package com.codewithseth.api.system;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({ "flag", "code", "message", "data" })
public class ResultResponse {
    private boolean flag;
    private int code;
    private String message;
    private Object data;

    public ResultResponse(boolean flag, int code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
