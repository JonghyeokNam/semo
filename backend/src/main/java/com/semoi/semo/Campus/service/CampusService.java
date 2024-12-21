package com.semoi.semo.Campus.service;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.Campus.enums.CampusName;

public interface CampusService {
    Campus getCampusOrElseThrow(CampusName campusName);
}
