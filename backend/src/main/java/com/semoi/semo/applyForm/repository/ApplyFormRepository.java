package com.semoi.semo.applyForm.repository;

import com.semoi.semo.applyForm.entity.ApplyForm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {

    // 특정 게시글의 신청서 목록 조회
    List<ApplyForm> findByBoardId(Long boardId);
}
