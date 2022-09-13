package com.springchallenge.gamebackend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="email")
    private String email;
    @Column(name="user_name")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="logged")
    private boolean logged;
    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "game")
    Set<GameState> games;


    public User (){
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now();
    }


}
