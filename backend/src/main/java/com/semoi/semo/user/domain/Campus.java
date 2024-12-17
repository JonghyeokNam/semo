package com.semoi.semo.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "campuses")
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campus_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "rec_score", nullable = false)
    private Integer recScore = 0;

    @Column(name = "act_score", nullable = false)
    private Integer actScore = 0;

    @OneToMany(mappedBy = "campus")
    private List<User> users;
}
