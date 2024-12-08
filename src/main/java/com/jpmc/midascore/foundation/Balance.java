package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@Data
@ToString
public class Balance {

    private long userId;

    private float amount;

    public Balance() {
    }

    public Balance(long userId, float amount) {
        this.userId = userId;
        this.amount = amount;
    }

//    @Override
//    public String toString() {
//        return "Balance {amount=" + amount + "}";
//    }
}