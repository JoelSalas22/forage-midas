package com.jpmc.midascore.service.listener;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.producer.KafkaProducer;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTransactionListener {

    private final TransactionRepository transactionRepository;

    private final TransactionService transactionService;

    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "${general.kafka-group-id}")
    public void processTransaction(String transaction) {

       // Handle the incoming transaction
        log.info("Received transaction: " + transaction);
        try {
            String[] transactionData = transaction.split(", ");
            Transaction newTransaction = new Transaction(Long.parseLong(transactionData[0]),
                    Long.parseLong(transactionData[1]), Float.parseFloat(transactionData[2]));

            // validate the transaction
            if (isValidTransaction(newTransaction)) {
                transactionService.processTransaction(newTransaction);
            } else {
                log.error("Invalid transaction: " + transaction);
                kafkaProducer.sendToDeadLetter(transaction);
            }
        } catch (Exception e) {
            log.error("Error processing transaction: " + transaction, e);
            kafkaProducer.sendToDeadLetter(transaction);
        }
    }


    @KafkaListener(topics = "${general.kafka-topic}", groupId = "${general.kafka-group-id}")
    public void listen(String transaction) {
        // Handle the incoming transaction
        log.info("Received transaction: {},\n" + transaction,
                "Sender ID: {},\n" + transaction.split(", ")[0],
                "Receiver ID: {},\n" + transaction.split(", ")[1],
                "Amount: {}\n" + transaction.split(", ")[2]
                );

        System.out.println("Received transaction: {}" + transaction);
        System.out.println("Sender ID: {}" + transaction.split(", ")[0]);
        System.out.println("Receiver ID: {}" + transaction.split(", ")[1]);
        System.out.println("Amount: {}" + transaction.split(", ")[2]);
    }


    private boolean isValidTransaction(Transaction transaction) {
        // Check if the transaction is valid
        long senderId = transaction.getSenderId();
        long recipientId = transaction.getRecipientId();
        float amount = transaction.getAmount();
        return amount > 0 && senderId > 0 && recipientId > 0;
    }
}
