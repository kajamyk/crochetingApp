package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.api.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/")
    public void addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{userName}/history")
    public void updateHistory(@Valid @PathVariable("userName") String userName, @Valid @RequestBody HistoryHolder historyHolder) {
        if (historyHolder.isAdd()) {
            userService.addTutorialToHistory(userName, historyHolder.tutorialName);
        } else {
            userService.removeTutorialFromHistory(userName, historyHolder.tutorialName);
        }
    }

    @DeleteMapping("/{userName}")
    public void deleteUser(@Valid @PathVariable("userName") String userName) {
        userService.deleteUser(userName);
    }

    @PutMapping("/{userName}")
    public void updateUser(@Valid @PathVariable("userName") String userName, @Valid @RequestBody UserHolder userHolder) {
        userService.updateUser(userName, userHolder.getPassword(), userHolder.getNewUserName(), userHolder.getNewPassword());
    }

    @PutMapping("/{userName}/courses")
    public void updateCourses(@Valid @PathVariable("userName") String userName, @Valid @RequestBody CourseHolder courseHolder) {
        if (courseHolder.isAdd()) {
            userService.addUserCourse(userName, courseHolder.courseType);
        } else {
            userService.deleteUserCourse(userName, courseHolder.courseType);
        }
    }

    @GetMapping("/{userName}")
    public User getUser(@Valid @PathVariable("userName") String userName) {
        return userService.getUser(userName);
    }

    @Data
    private static class HistoryHolder {
        private String tutorialName;
        private boolean add;
    }

    @Data
    private static class CourseHolder {
        private String courseType;
        private boolean add;
    }

    @Data
    private static class UserHolder {
        private String password;
        private String newPassword;
        private String newUserName;
    }

}
