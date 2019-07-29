package com.vironit.mwallet.model.entity;

import com.vironit.mwallet.model.attribute.WalletStatusEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "wallets")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "Can't be null")
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    @NotNull(message = "Can't be null")
    private Currency currency;

    @Column(name = "balance")
    @PositiveOrZero(message = "Can't be negative")
    private double balance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Can't be null")
    private WalletStatusEnum walletStatus = WalletStatusEnum.ACTIVE;

}
