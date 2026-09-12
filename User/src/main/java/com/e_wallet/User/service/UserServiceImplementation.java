package com.e_wallet.User.service;

import com.e_wallet.User.entity.User;
import com.e_wallet.User.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImplementation implements UserService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtService jwtService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${promotion.user.balance}")
    private float promotionBalance;

    @Override
    public User saveNewUser(User user){
        if(userRepository.findById(user.getId()).isEmpty()){
            user.setBalance(user.getBalance()+promotionBalance);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        sendNotification(user.getUsername(),user);
        return user;
    }

    @Override
    public void sendNotification(String userName, User user) {
        try{
            String json=objectMapper.writeValueAsString(user);
            System.out.println(json);
            kafkaTemplate.send(Constants.NEW_USER,userName,json);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
