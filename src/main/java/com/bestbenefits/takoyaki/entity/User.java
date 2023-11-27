package com.bestbenefits.takoyaki.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "in_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date inDate;

    @Column(name = "up_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date upDate;

    @Builder
    public User(String nickname, String password, String email){
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

}
