package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.Course;
import com.example.crochetingapp.core.Step;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.infra.api.services.CourseService;
import com.example.crochetingapp.infra.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("/{courseType}")
    public List<Tutorial> getCourseTutorials(@Valid @PathVariable("courseType") String courseType) throws Exception {
        try {
            return courseService.getCourseTutorials(courseType);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @GetMapping("/{courseType}/{tutorialName}")
    public Tutorial getTutorial(@Valid @PathVariable("courseType") String courseType, @Valid @PathVariable("tutorialName") String tutorialName) throws Exception {
        try {
            return courseService.getCourseTutorial(courseType, tutorialName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @GetMapping("/{courseType}/{tutorialName}/{step}")
    public Step getTutorialStep(@Valid @PathVariable("tutorialName") String tutorialName, @Valid @PathVariable("step") int step) throws Exception {
        try {
            return courseService.getCourseTutorialStep(tutorialName, step);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @PutMapping("/{courseType}")
    public void addCourse(@Valid @PathVariable("courseType") String courseType) throws Exception {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userName = userDetails.getUsername();
            userService.addUserCourse(userName, courseType);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @PutMapping("/{courseType}/{tutorialName}")
    public void addTutorial(@Valid @PathVariable("tutorialName") String tutorialName) throws Exception {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userName = userDetails.getUsername();
            userService.addTutorialToHistory(userName, tutorialName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
