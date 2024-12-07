package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.producer.KafkaProducer;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.service.TransactionService;
import com.jpmc.midascore.service.listener.KafkaTransactionListener;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaTransactionListener kafkaListener;
    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void task_three_verifier() throws InterruptedException {
        /* Check the following to ensure a transaction is valid:
    The senderId is valid
    The recipientId is valid
    The sender has a balance greater than or equal to the transaction amount
         */
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
            kafkaListener.processTransaction(transactionLine);
        }
        Thread.sleep(2000);



        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what waldorf's balance is after all transactions are processed");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("kill this test once you find the answer");
        UserRecord waldorf = userRepository.findByName("waldorf");
        logger.info("Waldorf's Information: " + waldorf);
        logger.info("Waldorf's Balance: " + transactionService.getUserBalanceByName("waldorf"));
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}
