package com.example.crochetingapp.infra.api.rest;

import com.example.crochetingapp.core.AuthRequest;
import com.example.crochetingapp.core.User;
import com.example.crochetingapp.infra.api.security.JwtUtil;
import com.example.crochetingapp.infra.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ModelAndView generateToken(@Valid AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            HttpSession session = httpSessionFactory.getObject();
            session.setAttribute("Authorization", "Bearer " + jwtUtil.generateToken(authRequest.getUserName()));
            //return jwtUtil.generateToken(authRequest.getUserName());
            String url = "redirect:/user/"+authRequest.getUserName();
            //return redirectToUserPage(authRequest.getUserName());
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName(url);
            return modelAndView;

        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return null;
    }

    @GetMapping("/tutorial")
    public ModelAndView redirectToUserPage () {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName = userDetails.getUsername();
        String url = "redirect:/user/" + userName;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(url);
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView createAccount(@Valid AuthRequest authRequest) throws Exception {
        try {
            User user = new User(authRequest.getUserName(), authRequest.getPassword());
            userService.addUser(user);
            return generateToken(authRequest);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    @GetMapping("/home")
    public ModelAndView getHome () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("homePage");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView redirectToRegister() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registerPage");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView redirectToLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

    @GetMapping("/user/{username}")
    public ModelAndView redirectToUserPage(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPage");
        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout() {
        HttpSession session = httpSessionFactory.getObject();
        session.removeAttribute("Authorization");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
