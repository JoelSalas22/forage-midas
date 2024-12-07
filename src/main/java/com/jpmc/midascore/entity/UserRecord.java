package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Data
@Setter
@ToString
@Builder
@AllArgsConstructor
@Table(name = "users")
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Column(name = "user_name", nullable = false)
    private String name;

    @Setter
    @Column(name = "balance", nullable = false)
    private float balance;

//    @OneToMany(mappedBy = "sender")
//    private List<TransactionRecord> transactions;

    protected UserRecord() {
    }

    public UserRecord(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

}
