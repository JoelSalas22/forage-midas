package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jpmc.midascore.entity.UserRecord;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class Transaction {
    private long senderId;
    private long recipientId;
    private float amount;

    public Transaction() {
    }

    public Transaction(long senderId, long recipientId, float amount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction {senderId=" + senderId + ", recipientId=" + recipientId + ", amount=" + amount + "}";
    }
}
