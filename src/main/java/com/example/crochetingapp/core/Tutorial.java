package com.example.crochetingapp.core;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
public class Tutorial {
    @Id
    @Column(name = "tutorial_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutorialId;
    private String tutorialName;
    @Column(columnDefinition = "LONGTEXT")
    private String requirements;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = EAGER)
    private List<Step> steps = new ArrayList<>();
}
