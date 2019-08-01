package com.vironit.mwallet.model.entity;

import com.vironit.mwallet.model.attribute.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTIONS_DETAILS")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Can't be null")
    private TransactionStatus status = TransactionStatus.CREATED;

    @Column(name = "createdAt")
    @NotNull(message = "Can't be null")
    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt")
    @NotNull(message = "Can't be null")
    @PastOrPresent
    private LocalDateTime updatedAt = LocalDateTime.now();

}
