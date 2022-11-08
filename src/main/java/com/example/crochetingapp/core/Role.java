package com.example.crochetingapp.core;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
}
