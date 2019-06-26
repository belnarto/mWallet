package com.vironit.mWallet.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "wallets")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Builder.Default
    @PositiveOrZero(message = "Can't be negative")
    private int id = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Can't be null")
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    @NotNull(message = "Can't be null")
    private Currency currency;

    @Column(name = "balance")
    @Builder.Default
    @PositiveOrZero(message = "Can't be negative")
    private double balance = 0;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @NotNull(message = "Can't be null")
    private WalletStatusEnum status = WalletStatusEnum.ACTIVE;

}
