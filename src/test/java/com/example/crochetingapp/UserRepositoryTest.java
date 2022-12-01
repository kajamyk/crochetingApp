package com.example.crochetingapp;


import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.api.services.UserService;
import com.example.crochetingapp.infra.jpa.JpaUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    JpaUserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUserTest() throws Exception {
        User user = new User();
        user.setUserName("marin");
        user.setPassword("marcin1");

        userService.addUser(user);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void addUserForNoUserNameTest() {
        User user = new User();
        user.setPassword("marcin1");

        assertThrows(Exception.class, () -> userService.addUser(user));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void addUserForNoPasswordTest() {
        User user = new User();
        user.setUserName("marcin1");

        assertThrows(Exception.class, () -> userService.addUser(user));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void addUserForNotAvailableUserName() throws Exception {
        User user = new User();
        user.setUserName("marcin1");
        user.setPassword("marcin1");
        List<User> users = new ArrayList<>();
        users.add(user);

        User newUser = new User();
        newUser.setUserName("marcin1");
        newUser.setPassword("marcin1");

        userService.addUser(user);

        verify(userRepository, times(1)).save(any());
        doReturn(users).when(userRepository).findAll();
        assertThrows(Exception.class, () -> userService.addUser(newUser));
    }

    @Test
    public void deleteUserTest() throws Exception {
        User user = new User();
        user.setUserName("marcin1");
        user.setPassword("marcin1");
        List<User> users = new ArrayList<>();
        users.add(user);

        doReturn(users).when(userRepository).findAll();
        doReturn(Optional.of(user)).when(userRepository).findById(user.getUserId());
        userService.deleteUser(user.getUserName());

        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    public void deleteUserWhenNoUserWithIdTest() {
        User user = new User();
        user.setUserName("marcin1");
        user.setPassword("marcin1");
        assertThrows(Exception.class, () -> userService.deleteUser(user));
    }

    @Test
    public void getUserTest() {
        User user = new User();
        user.setUserName("marcin1");
        user.setPassword("marcin1");

        doReturn(Optional.of(user)).when(userRepository).findById(user.getUserId());
        User userFromServer = userService.getUser(user.getUserId());

        assertEquals(user, userFromServer);
    }

    @Test
    public void getUserWhenNoUserWithIdTest() {
        User user = new User();
        user.setUserName("marcin1");
        user.setPassword("marcin1");

        assertThrows(Exception.class, () -> userService.getUser(user.getUserId()));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void updateUserTest() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUserName("marcin1");
        String password = passwordEncoder.encode("marcin1");
        user.setPassword(password);
        List<User> users = new ArrayList<>();
        users.add(user);

        doReturn(users).when(userRepository).findAll();

        userService.updateUser(user.getUserName(), "marcin1", "m1", "m1");

        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void updateUserWhenBadPasswordTest() {
        User user = new User();
        user.setUserName("marcin1");
        user.setPassword("marcin1");
        List<User> users = new ArrayList<>();
        users.add(user);
        doReturn(users).when(userRepository).findAll();

        assertThrows(Exception.class, () -> userService.updateUser(user.getUserName(), "m1", "m1", "m1"));
        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void updateUserWhenNoUserWithIdTest() {
        User user = new User();
        user.setUserName("marcin1");
        user.setPassword("marcin1");

        assertThrows(Exception.class, () -> userService.updateUser(user.getUserName(), "marcin1", "m1", "m1"));
        verify(userRepository, times(1)).findAll();
        verify(userRepository, times(0)).save(any());
    }
}
