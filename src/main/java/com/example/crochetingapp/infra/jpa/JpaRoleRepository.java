package com.example.crochetingapp.infra.jpa;


import com.example.crochetingapp.core.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends JpaRepository<Role, Long> {
}
