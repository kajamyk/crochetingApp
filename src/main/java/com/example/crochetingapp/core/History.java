package com.example.crochetingapp.core;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false)
    Long historyId;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = EAGER)
    private List<Tutorial> tutorials = new ArrayList<>();

    public History() {

    }
}
