package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.Course;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.infra.api.services.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping("/{userName}/history")
    public void updateHistory(@Valid @PathVariable("userName") String userName, @Valid @RequestBody String tutorialName) throws Exception {
        System.out.println(tutorialName);
        try {
            userService.removeTutorialFromHistory(userName, tutorialName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @DeleteMapping("/{userName}")
    public void deleteUser(@Valid @PathVariable("userName") String userName) throws Exception {
        try {
            userService.deleteUser(userName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

    }

    @PutMapping("/{userName}")
    public void updateUser(@Valid @PathVariable("userName") String userName, @Valid @RequestBody UserHolder userHolder) throws Exception {
        try {
            userService.updateUser(userName, userHolder.getPassword(), userHolder.getNewUserName(), userHolder.getNewPassword());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

    }

    @PutMapping("/{userName}/courses")
    public void updateCourses(@Valid @PathVariable("userName") String userName, @Valid @RequestBody String courseType) throws Exception {
        try {
            userService.deleteUserCourse(userName, courseType);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @GetMapping("/{userName}/history")
    public List<Tutorial> getHistory(@Valid @PathVariable("userName") String userName) {
        try {
            return userService.getUserTutorials(userName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @GetMapping("/{userName}/courses")
    public List<Course> getUserCourses(@Valid @PathVariable("userName") String userName) {
        try {
            return userService.getUserCourses(userName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }
    @Data
    private static class UserHolder {
        private String password;
        private String newPassword;
        private String newUserName;
    }
}
