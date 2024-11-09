package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class UserRecord {

    @Id
    @GeneratedValue()
    private long id;

    @Getter
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false)
    private float balance;

    protected UserRecord() {
    }

    public UserRecord(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

//    @Override
//    public String toString() {
//        return String.format("User[id=%d, name='%s', balance='%f'", id, name, balance);
//    }

    public Long getId() {
        return id;
    }

}
