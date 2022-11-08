package com.example.crochetingapp.infra.jpa;

import com.example.crochetingapp.core.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaHistoryRepository extends JpaRepository<History, Long> {
}
