package com.example.crochetingapp.infra.api;

import com.example.crochetingapp.core.Course;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.infra.jpa.JpaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private JpaCourseRepository courseRepository;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByType(String typeOfCourse) {
        List<Course> courses = getCourses();
        for (Course course : courses) {
            if (course.getTypeOfCourse().equals(typeOfCourse)) {
                return course;
            }
        }
        return null;
    }

    public List<Tutorial> getCourseTutoials(String typeOfCourse) {
        Course course = getCourseByType(typeOfCourse);
        return course.getTutorials();
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }
}
