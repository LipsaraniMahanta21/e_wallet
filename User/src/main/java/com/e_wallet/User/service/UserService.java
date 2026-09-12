package com.e_wallet.User.service;

import com.e_wallet.User.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveNewUser(User user);
    void sendNotification(String userName,User user);
    String generateToken(String userName);
    void validateToken(String token);
}