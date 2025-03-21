package com.semoi.semo.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 차현철
@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("admin"),
    PARTNER("partner"),
    USER("user");

    private final String role;
}
