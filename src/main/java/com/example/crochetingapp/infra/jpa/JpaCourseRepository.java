package com.example.crochetingapp.infra.jpa;

import com.example.crochetingapp.core.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCourseRepository extends JpaRepository<Course, Long> {
}
