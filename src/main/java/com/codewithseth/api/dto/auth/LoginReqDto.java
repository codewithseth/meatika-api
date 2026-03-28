package com.codewithseth.api.dto.auth;

import com.codewithseth.api.dto.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginReqDto (

    @NotNull(message = "Account type is required")
    AccountType type,

    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Password is required")
    String password

) {}
