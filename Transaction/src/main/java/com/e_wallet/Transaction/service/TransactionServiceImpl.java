package com.e_wallet.Transaction.service;

import com.e_wallet.Transaction.dto.MailDto;
import com.e_wallet.Transaction.entity.Transaction;
import com.e_wallet.Transaction.entity.TransactionType;
import com.e_wallet.Transaction.entity.User;
import com.e_wallet.Transaction.repository.TransactionRepository;
import com.e_wallet.Transaction.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void transferFund(int from, int to, float amount) {
        User user1=userRepository.findById(from).orElseThrow(()->new RuntimeException("User not found"));
        User user2=userRepository.findById(to).orElseThrow(()->new RuntimeException("User not found"));

        user1.setBalance(user1.getBalance()-amount);
        user2.setBalance(user2.getBalance()+amount);

        Transaction t1=new Transaction();
        t1.setUserId(from);
        t1.setAnotherId(to);
        t1.setTransactionType(TransactionType.DEBIT);
        t1.setAmount(amount);

        Transaction t2=new Transaction();
        t2.setUserId(to);
        t2.setAnotherId(from);
        t2.setTransactionType(TransactionType.CREDIT);
        t2.setAmount(amount);

        transactionRepository.save(t1);
        transactionRepository.save(t2);

        MailDto m1=new MailDto();
        m1.setId(user1.getId());
        m1.setMailId(user1.getEmail());
        m1.setAmount(t1.getAmount());
        m1.setTransactionType("DEBIT");

        MailDto m2=new MailDto();
        m2.setId(user2.getId());
        m2.setMailId(user2.getEmail());
        m2.setAmount(t2.getAmount());
        m2.setTransactionType("CREDIT");

        sendMail(m1,user1.getUsername());
        sendMail(m2,user2.getUsername());
    }

    private void sendMail(MailDto mail, String username) {
        try{
            String message=objectMapper.writeValueAsString(mail);
            kafkaTemplate.send("TRANSACTION", username, message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public User searchUserByName(String userName) {

        return userRepository.findByUserName(userName).orElseThrow(()->new RuntimeException("Invalid Username provided"));
    }
}
