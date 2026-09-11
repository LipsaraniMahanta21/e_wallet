package com.e_wallet.User.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private float balance;
    private String Role;
    private String password;
}