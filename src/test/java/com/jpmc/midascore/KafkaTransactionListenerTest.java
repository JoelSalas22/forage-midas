//package com.jpmc.midascore;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class KafkaTransactionListenerTest {
//
//    @Value("${general.kafka-topic}")
//    private String topic;
//
//
//    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
//    public void listen(String transaction) {
//       // Handle the incoming transaction
//        log.info("Received transaction, this is using Slf4j for logging: " + transaction);
//
//    }
//
//
//}