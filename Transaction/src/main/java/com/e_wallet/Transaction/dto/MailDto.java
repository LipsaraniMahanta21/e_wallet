package com.e_wallet.Transaction.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDto {
    @Email
    private String mailId;
    private int id;
    @NotEmpty
    private String transactionType;
    @NotNull
    private float amount;


}
