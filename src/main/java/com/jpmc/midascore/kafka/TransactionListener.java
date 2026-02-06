package com.jpmc.midascore.kafka;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.TransactionRecord;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionListener(UserRepository userRepository,
                               TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
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

// update balances
        sender.setBalance(senderBalance - txAmount);
        recipient.setBalance(recipientBalance + txAmount);






        userRepository.save(sender);
        userRepository.save(recipient);

        // Save transaction record
        TransactionRecord record =
                new TransactionRecord(sender, recipient, transaction.getAmount());

        transactionRecordRepository.save(record);
    }
}
