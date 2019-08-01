package com.vironit.mwallet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "PAYMENT_TRANSACTIONS")
@PrimaryKeyJoinColumn(foreignKey=@ForeignKey(name = "payment_transactions_details_id_fk"))
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class PaymentTransaction extends Transaction {

    @Column(name = "wallet_id")
    @Positive(message = "Can't be negative or zero")
    private int walletId;

    @Column(name = "amount")
    @Positive(message = "Can't be negative or zero")
    private double amount;

    @Column(name = "account_id")
    @Positive(message = "Can't be negative or zero")
    private int accountId;

}
