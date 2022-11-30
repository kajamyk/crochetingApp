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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@Slf4j
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ModelAndView getCourses() {

        //return courseService.getCourses();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("coursesPage");
        return modelAndView;
    }

    @GetMapping("/{courseType}")
    public ModelAndView getCourseTutorials(@Valid @PathVariable("courseType") String courseType) throws Exception {
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("tutorialsPage");
            modelAndView.addObject("tutorials", courseService.getCourseTutorials(courseType.toUpperCase()));
            modelAndView.addObject("courseType",courseType);
            return modelAndView;
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @GetMapping("/{courseType}/{tutorialName}")
    public ModelAndView getTutorial(@Valid @PathVariable("courseType") String courseType, @Valid @PathVariable("tutorialName") String tutorialName) throws Exception {
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("tutorialPage");
            modelAndView.addObject("tutorial", courseService.getCourseTutorial(courseType, tutorialName));
            return modelAndView;
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @GetMapping("/{courseType}/{tutorialName}/{step}")
    public ModelAndView getTutorialStep(@Valid @PathVariable("tutorialName") String tutorialName, @Valid @PathVariable("step") int step, @Valid @PathVariable("courseType") String courseType) throws Exception {
        try {


            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("tutorialStepPage");
            modelAndView.addObject("number", step);
            modelAndView.addObject("steps", courseService.getCourseTutorial(courseType, tutorialName).getSteps().size());
            modelAndView.addObject("step", courseService.getCourseTutorialStep(tutorialName, step));
            return modelAndView;

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

    @PostMapping("/{courseType}/{tutorialName}")
    public ModelAndView addTutorial(@Valid @PathVariable("tutorialName") String tutorialName, @Valid @PathVariable("courseType") String courseType) throws Exception {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userName = userDetails.getUsername();
            try {
                userService.addTutorialToHistory(userName, tutorialName);
            } catch (Exception e){

            }
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("tutorialStepPage");
            modelAndView.addObject("steps", courseService.getCourseTutorial(courseType, tutorialName).getSteps().size());
            modelAndView.addObject("number", 1);
            modelAndView.addObject("step", courseService.getCourseTutorialStep(tutorialName, 1));
            return modelAndView;
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}
