package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.producer.KafkaProducer;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.service.listener.KafkaTransactionListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskFourTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private KafkaTransactionListener kafkaListener;


//    @BeforeAll
//    public static void startIncentiveApi() throws IOException {
//        // start the incentive api
//        logger.info("Starting the Incentive API");
//        Runtime.getRuntime().exec("java -jar ./services/transaction-incentive-api.jar");
//        logger.info("Incentive API starting...");
//        // Ping the Incentive API
//        logger.info("Pinging the Incentive API");
//        // log the response
//        logger.info("Incentive API response: {}", Runtime.getRuntime().exec("curl " +
//                "http://localhost:8080"));
//
//    }

    @Test
    void task_four_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
            kafkaListener.processTransaction(transactionLine);
        }
        Thread.sleep(2000);


        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what wilbur's balance is after all transactions are processed");
        // figure out the balance of the “wilbur”
        UserRecord wilbur = userRepository.findByName("wilbur");
        logger.info("Wilbur's balance is: " + wilbur.getBalance());
        logger.info("kill this test once you find the answer");
        logger.info("----------------------------------------------------------");
        while (true) {
            Thread.sleep(20000);
            logger.info("...");
        }
    }
}
