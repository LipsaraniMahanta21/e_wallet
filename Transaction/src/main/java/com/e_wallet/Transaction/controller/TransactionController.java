package com.e_wallet.Transaction.controller;

import com.e_wallet.Transaction.dto.TransactionDto;
import com.e_wallet.Transaction.entity.User;
import com.e_wallet.Transaction.service.TransactionService;
//import com.e_wallet.Transaction.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer")
    public TransactionDto transfer(@RequestHeader("loggedInUser")String user,
                                   @RequestBody TransactionDto transaction) {
        User u=transactionService.searchUserByName(user);
        transactionService.transferFund(u.getId(),transaction.getTo(),transaction.getAmount());
        return transaction;
    }

    @GetMapping("transfer/test")
    public String test(@RequestHeader("loggedInUser")String user){
        return "Transaction Test "+user;
    }
}
