package com.semoi.semo.applyForm.repository;

import com.semoi.semo.applyForm.entity.ApplyForm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {

    // 특정 게시글의 신청서 목록 조회
    List<ApplyForm> findByBoardId(Long boardId);

    // 특정 사용자 ID에 해당하는 모든 신청서 조회
    List<ApplyForm> findByUserId(Long userId);

    @Query("SELECT a FROM ApplyForm a WHERE a.applyFormId = :applyFormId AND a.userId = :userId")
    ApplyForm findByApplyFormIdAndUserId(@Param("applyFormId") Long applyFormId, @Param("userId") Long userId);

    boolean existsByBoardIdAndUserId(Long boardId, Long userId);

    @Query("SELECT COUNT(a) FROM ApplyForm a JOIN a.position p " +
            "WHERE a.boardId = :boardId AND p.name = :positionName")
    int countByBoardIdAndPositionName(@Param("boardId") Long boardId, @Param("positionName") String positionName);
}
