package com.vironit.mwallet.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "MONEY_TRANSFER_TRANSACTIONS")
@PrimaryKeyJoinColumn(foreignKey=@ForeignKey(name = "money_transfer_transactions_details_id_fk"))
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class MoneyTransferTransaction extends Transaction {

    @Column(name = "from_wallet_id")
    @Positive(message = "Can't be negative or zero")
    private int fromWalletId;

    @Column(name = "to_wallet_id")
    @Positive(message = "Can't be negative or zero")
    private int toWalletId;

    @Column(name = "amount")
    @Positive(message = "Can't be negative or zero")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    @NotNull(message = "Can't be null")
    private Currency currency;

}
