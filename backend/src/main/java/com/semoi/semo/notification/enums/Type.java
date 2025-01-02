package com.semoi.semo.notification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    PARTICIPATION_REQUEST("참여 신청", "참여 신청이 왔어요!"),
    RECRUITMENT_PERIOD_END("모집 기간 종료", "제안하신 모집의 기간이 종료되었어요!"),
    PARTICIPATION_RESULT("참여 결과 알림", "지원 결과가 도착했어요!"),
    COMMENT_ALERT("댓글 알림", "작성한 게시글에 댓글이 달렸어요!");

    private final String title;
    private final String content;
}
