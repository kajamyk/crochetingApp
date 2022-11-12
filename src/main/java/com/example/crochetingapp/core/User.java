package com.example.crochetingapp.core;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    @Column(unique = true, name = "user_name")
    private String userName;
    @NotNull
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private History history = new History();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    List<Course> courses = new ArrayList<>();

    @NotNull
    private String role;

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.role = "USER";
    }
}
