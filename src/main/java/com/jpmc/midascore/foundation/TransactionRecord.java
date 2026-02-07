package com.jpmc.midascore.foundation;

import com.jpmc.midascore.entity.UserRecord;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    private float amount;

    private float incentive;

    private Instant timestamp = Instant.now();;

    public TransactionRecord() {}
    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }

    public TransactionRecord(UserRecord sender,
                             UserRecord recipient,
                             float amount,
                             float incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
        this.timestamp = Instant.now();
    }

}
