package com.codewithseth.api.dto;

public record AccountDto (
    Integer id,
    String uuid,
    String username,
    String type,
    String isActive,
    String twoFaEnabled
) {}
