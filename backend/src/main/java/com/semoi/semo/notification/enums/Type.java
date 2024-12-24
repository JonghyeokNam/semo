package com.semoi.semo.notification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    PARTICIPATION_REQUEST("참여 신청 알림"),
    RECRUITMENT_PERIOD_END("모집 기간이 종료 되었습니다."),
    COMMENT_ALERT("댓글이 작성되었습니다."),
    SIGN_UP("환영합니다."),
    PARTICIPATION_DECISION("우리 함께해요.");

    private final String type;
}
