package com.example.crochetingapp.infra.api;

import com.example.crochetingapp.core.Course;
import com.example.crochetingapp.core.History;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.jpa.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    JpaUserRepository userRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    TutorialService tutorialService;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUser(String userName) {
        User user = getUser(userName);
        if (user != null) {
            deleteUser(user);
        }
    }

    public User getUser(String userName) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void updateUser(String userName, String password, String newUserName, String newPassword) {
        User user = getUser(userName);
        if (user == null) {
            return;
        }
        if (password.equals(user.getPassword())) {
            user.setUserName(newUserName);
            user.setPassword(newPassword);
            addUser(user);
        }
    }

    public void addTutorialToHistory(String userName, String tutorialName) {
        User user = getUser(userName);
        if (user == null) {
            return;
        }
        History history = user.getHistory();
        Tutorial tutorial = tutorialService.getTutorialByName(tutorialName);
        if (tutorial == null) {
            return;
        }
        history.getTutorials().add(tutorial);
        user.setHistory(history);
        addUser(user);
    }

    public void removeTutorialFromHistory(String userName, String tutorialName) {
        User user = getUser(userName);
        if (user == null) {
            return;
        }
        History history = user.getHistory();
        Tutorial tutorial = tutorialService.getTutorialByName(tutorialName);
        if (tutorial == null) {
            return;
        }
        history.getTutorials().remove(tutorial);
        user.setHistory(history);
        addUser(user);
    }

    public void addUserCourse(String userName, String courseType) {
        User user = getUser(userName);
        if (user == null) {
            return;
        }
        Course course = courseService.getCourseByType(courseType);
        if (course == null) {
            return;
        }
        user.getCourses().add(course);
        addUser(user);
    }

    public void deleteUserCourse(String userName, String courseType) {
        User user = getUser(userName);
        if (user == null) {
            return;
        }
        Course course = courseService.getCourseByType(courseType);
        if (course == null) {
            return;
        }
        user.getCourses().remove(course);
        addUser(user);
    }
}
