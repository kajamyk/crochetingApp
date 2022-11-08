package com.example.crochetingapp;

import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.jpa.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class CrochetingAppApplication {

    @Autowired
    JpaUserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(CrochetingAppApplication.class, args);
    }

    @PostConstruct
    public void initUsers() {
        User user = new User("kaja", "kaja");
        List<User> users = new ArrayList<>();
        users.add(user);
        userRepository.saveAll(users);
    }

}
