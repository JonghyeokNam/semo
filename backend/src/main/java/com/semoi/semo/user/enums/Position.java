package com.semoi.semo.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {

    BACKEND("backend"),
    FRONTEND("frontend"),
    MARKETER("marketer"),
    PLANNER("planner"),
    DESIGNER("designer"),
    UNDECIDED("undecided");

    private final String position;
}
