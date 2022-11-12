package com.example.crochetingapp.infra.api.services;

import com.example.crochetingapp.core.Course;
import com.example.crochetingapp.core.History;
import com.example.crochetingapp.core.Tutorial;
import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.jpa.JpaUserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    @Autowired
    JpaUserRepository userRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    TutorialService tutorialService;


    private String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private boolean arePasswordsMatching(String rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void addUser(User user) throws Exception {
        if (user.getUserName() == null || user.getPassword() == null) {
            throw new Exception("Password and login cannot be null!");
        }
        if (getUser(user.getUserName()) != null) {
            throw new Exception("This user name is already taken!");
        }
        user.setPassword(encodePassword(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user) throws Exception {
        if (user.getUserName() == null || user.getPassword() == null) {
            throw new Exception("Password and login cannot be null!");
        }
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(User user) throws Exception {
        if (user == null || getUser(user.getUserId()) == null) {
            throw new Exception("No user was found!");
        }
        if (user.getUserName() == null || user.getPassword() == null) {
            throw new Exception("Password and login cannot be null!");
        }
        userRepository.delete(user);
    }

    public void deleteUser(String userName) throws Exception {
        User user = getUser(userName);
        if (user == null) {
            throw new Exception("No user was found!");
        }
        deleteUser(user);
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

    public void updateUser(String userName, String password, String newUserName, String newPassword) throws Exception {
        User user = getUser(userName);
        if (user == null) {
            throw new Exception("No user was found!");
        }
        if (!arePasswordsMatching(password, user.getPassword())) {
            throw new Exception("Bad password!");
        }
        user.setUserName(newUserName);
        user.setPassword(encodePassword(newPassword));
        updateUser(user);
    }

    public void addTutorialToHistory(String userName, String tutorialName) throws Exception {
        User user = getUser(userName);
        if (user == null) {
            throw new Exception("No user was found!");
        }
        History history = user.getHistory();
        Tutorial tutorial = tutorialService.getTutorialByName(tutorialName);
        if (tutorial == null) {
            throw new Exception("No tutorial was found!");
        }
        history.getTutorials().add(tutorial);
        user.setHistory(history);
        updateUser(user);
    }

    public void removeTutorialFromHistory(String userName, String tutorialName) throws Exception {
        User user = getUser(userName);
        if (user == null) {
            throw new Exception("No user was found!");
        }
        History history = user.getHistory();
        Tutorial tutorial = tutorialService.getTutorialByName(tutorialName);
        if (tutorial == null) {
            throw new Exception("No tutorial was found!");
        }
        history.getTutorials().remove(tutorial);
        user.setHistory(history);
        updateUser(user);
    }

    public void addUserCourse(String userName, String courseType) throws Exception {
        User user = getUser(userName);
        if (user == null) {
            throw new Exception("No user was found!");
        }
        Course course = courseService.getCourseByType(courseType);
        if (course == null) {
            throw new Exception("No course was found!");
        }
        user.getCourses().add(course);
        updateUser(user);
    }

    public void deleteUserCourse(String userName, String courseType) throws Exception {
        User user = getUser(userName);
        if (user == null) {
            throw new Exception("No user was found!");
        }
        Course course = courseService.getCourseByType(courseType);
        if (course == null) {
            throw new Exception("No course was found!");
        }
        user.getCourses().remove(course);
        updateUser(user);
    }

    public List<Tutorial> getUserTutorials(String userName) {
        User user = getUser(userName);
        return user.getHistory().getTutorials();
    }

    public List<Course> getUserCourses(String userName) {
        User user = getUser(userName);
        return user.getCourses();
    }

    public List<AlreadyRegisteredUser> getAlreadyRegisteredUsers() {
        List<AlreadyRegisteredUser> alreadyRegisteredUsers = new ArrayList<>();
        List<User> users = getUsers();

        for (User user : users) {
            AlreadyRegisteredUser alreadyRegisteredUser = new AlreadyRegisteredUser();
            alreadyRegisteredUser.setUserName(user.getUserName());
            alreadyRegisteredUser.setRole(user.getRole());
            alreadyRegisteredUser.setCourses(user.getCourses());
            alreadyRegisteredUser.setHistory(user.getHistory());
            alreadyRegisteredUsers.add(alreadyRegisteredUser);
        }
        return alreadyRegisteredUsers;
    }

    @NoArgsConstructor
    @Data
    public class AlreadyRegisteredUser {
        List<Course> courses;
        String role;
        private String userName;
        private History history;
    }
}
