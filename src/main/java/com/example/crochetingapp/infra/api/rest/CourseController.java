package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.Course;
import com.example.crochetingapp.infra.api.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping("/{courseType}")
    public Course getCourse(@Valid @PathVariable("courseType") String courseType) {
        return courseService.getCourseByType(courseType);
    }
}
