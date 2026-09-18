package com.e_wallet.Transaction.repository;

import com.e_wallet.Transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>
{
}
