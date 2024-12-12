package com.semoi.semo.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "login_email", nullable = false, length = 255)
    private String loginEmail;

    @Column(name = "user_email", nullable = false, length = 255)
    private String userEmail;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "position", nullable = false, length = 255)
    private String position;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Column(name = "campus_id", nullable = false)
    private Long campusId;

    @Column(name = "rec_score", nullable = false)
    private Integer recScore;

    @Column(name = "act_score", nullable = false)
    private Integer actScore;

    @Column(name = "role", nullable = false)
    private Integer role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.semoi.semo.board.entity.Board> boards; // 회원이 작성한 게시물들
}
