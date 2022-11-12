package com.example.crochetingapp.core;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Tutorial {
    @Id
    @Column(name = "tutorial_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutorialId;
    @NotNull
    private String tutorialName;
    @Column(columnDefinition = "LONGTEXT")
    private String requirements;
    @OneToMany()
    private List<Step> steps = new ArrayList<>();

    public Tutorial() {

    }
}
