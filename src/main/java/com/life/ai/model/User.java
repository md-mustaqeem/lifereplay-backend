package com.life.ai.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String mobile;

    @Column(unique = true)
    private String email;

    private String password;
}
