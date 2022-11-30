package com.example.crochetingapp.core;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
@Getter
public class Step {
    @Id
    @Column(name = "step_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepId;
    @NotNull
    private String nameOfStep;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    public Step() {

    }
}
