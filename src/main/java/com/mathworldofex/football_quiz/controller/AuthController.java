package com.mathworldofex.football_quiz.controller;

import com.mathworldofex.football_quiz.JwtUtils;
import com.mathworldofex.football_quiz.enity.ERole;
import com.mathworldofex.football_quiz.enity.Role;
import com.mathworldofex.football_quiz.enity.User;
import com.mathworldofex.football_quiz.payload.LoginRequest;
import com.mathworldofex.football_quiz.payload.SignupRequest;
import com.mathworldofex.football_quiz.payload.response.JwtResponse;
import com.mathworldofex.football_quiz.repository.RoleRepository;
import com.mathworldofex.football_quiz.repository.UserRepository;
import com.mathworldofex.football_quiz.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @ModelAttribute LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        final Cookie cookie = new Cookie("mwe_user", jwt);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 3);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ModelAndView registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) throws URISyntaxException {
        ModelAndView  mav = new ModelAndView("user/message");

        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            return mav.addObject("message", "Error: Username is already taken!");
        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            return mav.addObject("message", "Error: Email is already in use!");

        // Create new user's account
        User user = new User(
                signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = Collections.singleton("user");
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_UNPAID_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        mav.addObject("messageHeader", "Sign up status");
        return mav.addObject("message", "Successful: User created!");
    }


}
