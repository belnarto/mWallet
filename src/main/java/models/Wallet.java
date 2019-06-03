package models;

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

    private double balance;

    public Wallet() {}

    public Wallet(User user, Currency currency) {
        setUser(user);
        setCurrency(currency);
    }

    public void setId(int newId) {
        if(newId>0) {
            id = newId;
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public int getId() {
        return id;
    }

    public void setUser(User newUser) {
        if(newUser != null) {
            user = newUser;
        } else {
            throw new IllegalArgumentException("User is null");
        }
    }

    public User getUser() {
        return user;
    }

    public void setCurrency(Currency newCurrency) {
        if(newCurrency != null) {
            currency = newCurrency;
        } else {
            throw new IllegalArgumentException("Currency is null");
        }
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setBalance(double newBalance) {
        if(newBalance>=0) {
            balance = newBalance;
        } else {
            throw new IllegalArgumentException("Value < 0");
        }
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "\n\twallet id: "+id+"Currency: "+currency.getName()+" balance:"+balance;
    }

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof Wallet) ) {
            return false;
        }
        Wallet otherWallet = (Wallet) other;
        return this.id == otherWallet.getId() &&
                this.getUser().equals(otherWallet.getUser());
    }

}
