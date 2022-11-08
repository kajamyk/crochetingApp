package com.example.crochetingapp.core;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Step {
    @Id
    @Column(name = "step_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepId;
    private String nameOfStep;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
}
