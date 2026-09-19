package com.e_wallet.Transaction.service;

import com.e_wallet.Transaction.entity.User;
import org.springframework.stereotype.Service;

public interface TransactionService {
    void transferFund(int from, int to,float amount);
    User searchUserByName(String userName);
}
