package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;

import java.util.List;

public interface TransactionService {

    void processTransaction(Transaction transaction);


    void updateByBalanceId(long id, float balance);

    float getUserBalanceByName(String name);

}
