package com.example.crochetingapp.infra.jpa;

import com.example.crochetingapp.core.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStepRepository extends JpaRepository<Step, Long> {
}
