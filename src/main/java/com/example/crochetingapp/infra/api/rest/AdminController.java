package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<UserService.AlreadyRegisteredUser> getUsers() {
        try {
            return userService.getAlreadyRegisteredUsers();
        } catch(Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }
    @DeleteMapping("/{userName}")
    public void deleteUser(@Valid @PathVariable("userName") String userName) throws Exception {
        try {
            userService.deleteUser(userName);
        } catch(Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
