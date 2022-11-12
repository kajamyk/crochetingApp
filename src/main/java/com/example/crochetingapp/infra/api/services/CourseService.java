package com.example.crochetingapp.infra.api.services;

import com.example.crochetingapp.core.Course;
import com.example.crochetingapp.core.Step;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.infra.jpa.JpaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    TutorialService tutorialService;
    @Autowired
    private JpaCourseRepository courseRepository;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByType(String typeOfCourse) throws Exception {
        List<Course> courses = getCourses();
        for (Course course : courses) {
            if (course.getTypeOfCourse().equals(typeOfCourse)) {
                return course;
            }
        }
        throw new Exception("Course was not found!");
    }

    public List<Tutorial> getCourseTutorials(String typeOfCourse) throws Exception {
        Course course = getCourseByType(typeOfCourse);
        if (course == null) {
            throw new Exception("Course was not found!");
        }
        return course.getTutorials();
    }

    public Tutorial getCourseTutorial(String typeOfCourse, String tutorialName) throws Exception {
        List<Tutorial> tutorials = getCourseTutorials(typeOfCourse);
        for (Tutorial tutorial : tutorials) {
            if (tutorial.getTutorialName().equals(tutorialName)) {
                return tutorial;
            }
        }
        throw new Exception("Tutorial was not found!");
    }

    public Step getCourseTutorialStep(String tutorialName, int step) throws Exception {
        return tutorialService.getStep(tutorialName, step);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }
}
