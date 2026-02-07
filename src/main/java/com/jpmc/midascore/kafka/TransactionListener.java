package com.jpmc.midascore.kafka;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.TransactionRecord;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.jpmc.midascore.foundation.Incentive;

@Component
public class TransactionListener {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final RestTemplate restTemplate;

    public TransactionListener(UserRepository userRepository,
                               TransactionRecordRepository transactionRecordRepository,
                               RestTemplate restTemplate)
    {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void receive(Transaction transaction) {

        UserRecord sender =
                userRepository.findById(transaction.getSenderId());

        UserRecord recipient =
                userRepository.findById(transaction.getRecipientId());


        if (sender == null || recipient == null) return;

        float senderBalance = sender.getBalance();
        float recipientBalance = recipient.getBalance();
        float txAmount = transaction.getAmount();

// validation
        if (senderBalance < txAmount) return;

        // Call incentive API
        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        float incentiveAmount = incentive.getAmount();

// update balances
        sender.setBalance(senderBalance - txAmount);
        recipient.setBalance(recipientBalance + txAmount+ incentiveAmount);

        userRepository.save(sender);
        userRepository.save(recipient);

        // Save transaction record
        TransactionRecord record =
                new TransactionRecord(sender, recipient,
                        transaction.getAmount(), incentiveAmount);

        transactionRecordRepository.save(record);

    }
}


