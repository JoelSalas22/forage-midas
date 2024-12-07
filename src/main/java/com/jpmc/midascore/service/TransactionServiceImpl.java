package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.InvalidTransactionException;
import lombok.AllArgsConstructor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;


    @Override
    public void processTransaction(Transaction transaction) {
        try {
            // Validate sender and receiver
            Optional<UserRecord> senderOpt = Optional.ofNullable(userRepository.findById(transaction.getSenderId()));
            Optional<UserRecord> receiverOpt = Optional.ofNullable(userRepository.findById(transaction.getRecipientId()));

            if (senderOpt.toString().isEmpty() || senderOpt.toString().isBlank() || receiverOpt.toString().isEmpty()
                    || receiverOpt.toString().isBlank()) {
                log.error("Invalid sender or receiver for transaction: " + transaction);
                throw new InvalidTransactionException("Invalid sender or receiver for transaction: " + transaction);
            }
            // Set sender and receiver
            UserRecord sender = senderOpt.orElse(null);

            UserRecord receiver = receiverOpt.orElse(null);

            // Validate Sender Balance
            if (sender.getBalance() < transaction.getAmount()) {
                log.atInfo().log("Insufficient balance for transaction: " + transaction);
                log.info("Insufficient balance for transaction: {}, " +
                                "SenderId: {}, \n" +
                                "Sender Balance: {}",
                        transaction, transaction.getSenderId(), sender.getBalance());
                throw new InvalidTransactionException("Insufficient balance for transaction:" +
                        " " + transaction);
            }


            // Record the Transaction
            TransactionRecord transactionRecord = new TransactionRecord(sender, receiver, transaction.getAmount());
            transactionRecord.setSender(sender);
            transactionRecord.setRecipient(receiver);
            transactionRecord.setAmount(transaction.getAmount());
            transactionRepository.save(transactionRecord);
            log.info("Transaction Successfully Recorded: " + transactionRecord);
            // Adjust The Balances

            float senderNewBalance =
                    roundToTwoDecimalPlaces(sender.getBalance() - transaction.getAmount());
            float recipientNewBalance =
                    roundToTwoDecimalPlaces(receiver.getBalance() + transaction.getAmount());

            updateByBalanceId(sender.getId(), senderNewBalance);
            log.info("Sender's Name: {}", sender.getName());
            log.info("Sender's New Balance: {}", senderNewBalance);
            log.info("Sender Information: {}", sender);
            updateByBalanceId(receiver.getId(), recipientNewBalance);
            log.info("Recipient's Name: {}", receiver.getName());
            log.info("Recipient's New Balance: {}", recipientNewBalance);
            log.info("Recipient's Information: {}", receiver);

            // sender.setBalance(sender.getBalance() - transaction.getAmount());
            // receiver.setBalance(receiver.getBalance() + transaction.getAmount());

            // userRepository.save(sender);
            // userRepository.save(receiver);
        } catch (InvalidTransactionException error) {
            log.error("Error processing transaction: " + transaction, error);
        }
    }

    @Override
    public void updateByBalanceId(long id, float balance) {
        userRepository.updateBalanceById(id, balance);
    }


    public static float roundToTwoDecimalPlaces(float value) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        return bd.floatValue();
    }

    @Override
    public float getUserBalanceByName(String name) {
        UserRecord userRecord = userRepository.findByName(name);
        if (userRecord == null) {
            log.error("User not found: " + name);
            return -1;
        }
        return userRecord.getBalance();
    }

}
