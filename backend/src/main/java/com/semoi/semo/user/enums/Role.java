package com.semoi.semo.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("admin"), USER("user");

    private String role;
}
