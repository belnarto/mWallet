package com.vironit.mWallet.models;

import javax.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "balance")
    private double balance;

    // TODO оживить статус с точки зрения бизнес-логики
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WalletStatusEnum status = WalletStatusEnum.ACTIVE;

    public Wallet() {
    }

    public Wallet(User user, Currency currency) {
        setUser(user);
        setCurrency(currency);
    }

    public void setId(int newId) {
        if (newId > 0) {
            id = newId;
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public int getId() {
        return id;
    }

    public void setUser(User newUser) {
        if (newUser != null) {
            user = newUser;
        } else {
            throw new IllegalArgumentException("User is null");
        }
    }

    public User getUser() {
        return user;
    }

    public void setCurrency(Currency newCurrency) {
        if (newCurrency != null) {
            currency = newCurrency;
        } else {
            throw new IllegalArgumentException("Currency is null");
        }
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setBalance(double newBalance) {
        if (newBalance >= 0) {
            balance = newBalance;
        } else {
            throw new IllegalArgumentException("Value < 0");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setStatus(WalletStatusEnum status) {
        this.status = status; //TODO проверку на нуль
    }

    public WalletStatusEnum getStatus() {
        return status;
    }

    @SuppressWarnings("WeakerAccess")
    public void activateWallet() {
        status = WalletStatusEnum.ACTIVE;
    }

    @SuppressWarnings("WeakerAccess")
    public void blockWallet() {
        status = WalletStatusEnum.BLOCKED;
    }

    @Override
    public String toString() {
        return "\n\twallet id: " + id
                + "Currency: " + currency.getName()
                + " balance:" + balance
                + " status:" + status;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Wallet)) {
            return false;
        }
        Wallet otherWallet = (Wallet) other;
        return this.id == otherWallet.getId() &&
                this.status.equals(otherWallet.status) &&
                this.getUser().equals(otherWallet.getUser());
    }

}
