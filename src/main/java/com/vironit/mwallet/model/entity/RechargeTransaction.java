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
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "RECHARGE_TRANSACTIONS")
@PrimaryKeyJoinColumn(foreignKey=@ForeignKey(name = "recharge_transactions_details_id_fk"))
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class RechargeTransaction extends Transaction {

    @Column(name = "wallet_id")
    @PositiveOrZero(message = "Can't be negative")
    private int walletId;

    @Column(name = "amount")
    @PositiveOrZero(message = "Can't be negative")
    private double amount;

}
