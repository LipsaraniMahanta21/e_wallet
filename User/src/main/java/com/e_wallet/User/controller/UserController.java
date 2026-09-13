package com.e_wallet.User.controller;

import com.e_wallet.User.dto.AuthRequest;
import com.e_wallet.User.dto.JwtResponse;
import com.e_wallet.User.entity.User;
import com.e_wallet.User.repository.UserRepository;
import com.e_wallet.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Auth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User addUser(@RequestBody User user) {
        return userService.saveNewUser(user);
    }
    @PostMapping("/login")
    public JwtResponse getToken(@RequestBody AuthRequest authRequest) {
        System.out.println("User"+authRequest.getUserName()+"logged in using password"+authRequest.getPassword());
        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));

        System.out.println(authentication.isAuthenticated());
        if(authentication.isAuthenticated()){
            String token=userService.generateToken(authRequest.getUserName());
            User user=userRepository.findByUsername(authRequest.getUserName()).get();
            JwtResponse jwtResponse =new JwtResponse();
            jwtResponse.setToken(token);
            jwtResponse.setRole(user.getRole());

            return jwtResponse;
        }else{
            throw new RuntimeException("Invalid username or password");
        }
    }
}
