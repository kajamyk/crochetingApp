package com.example.crochetingapp.infra.jpa;

import com.example.crochetingapp.core.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTutorialRepository extends JpaRepository<Tutorial, Long> {
}
