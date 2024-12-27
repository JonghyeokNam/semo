package com.semoi.semo.campus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CampusName {

    JONGNO("종로"),
    SEONGDONG("성동"),
    SEONGBUK("성북"),
    DONGDAEMUN("동대문"),
    DOBONG("도봉"),
    GANGBUK("강북"),
    GWANAK("관악"),
    NOWON("노원"),
    EUNPYEONG("은평"),
    GANGNAM("강남"),
    YEONGDEUNGPO("영등포"),
    GEUMCHEON("금천"),
    MAPO("마포"),
    YONGSAN("용산"),
    GANGDONG("강동"),
    GANGSEO("강서"),
    DONGJAK("동작"),
    SEODAEMUN("서대문"),
    GWANGJIN("광진"),
    JUNGGU("중구");

    private final String name;
}

