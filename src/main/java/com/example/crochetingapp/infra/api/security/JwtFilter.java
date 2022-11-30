package com.example.crochetingapp.infra.api.security;

import com.example.crochetingapp.infra.api.services.CustomUserDetailsService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //httpServletResponse.setHeader("Authorization", "Bearer " +"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbGExMjMiLCJleHAiOjE2Njk1MjY1NDgsImlhdCI6MTY2OTQ5MDU0OH0.k6-xwbhWja1ZXeaRL3lHOc1PN9D8dMQ1UazYmldRIjQ");
        //String authorizationHeader = httpServletRequest.getHeader("Authorization");
        HttpSession session = httpSessionFactory.getObject();
        String authorizationHeader = (String) session.getAttribute("Authorization");

        //String authorizationHeader = "Bearer " +"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbGExMjMiLCJleHAiOjE2Njk1MjY1NDgsImlhdCI6MTY2OTQ5MDU0OH0.k6-xwbhWja1ZXeaRL3lHOc1PN9D8dMQ1UazYmldRIjQ";
        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtUtil.extractUsername(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);


            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
