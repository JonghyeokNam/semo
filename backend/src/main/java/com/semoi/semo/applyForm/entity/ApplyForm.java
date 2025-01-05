package com.semoi.semo.applyForm.entity;

import com.semoi.semo.position.entity.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "applyForm")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyForm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apply_form_seq")
    @SequenceGenerator(name = "apply_form_seq", sequenceName = "apply_form_seq", allocationSize = 1)
    @Column(name = "applyform_id", nullable = false)
    private Long applyFormId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Column(name = "about_me", length = 4000)
    private String aboutMe;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "board_id", nullable = false)
    private Long boardId;
}