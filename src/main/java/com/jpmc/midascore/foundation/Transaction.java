package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
