package com.vironit.mWallet.models;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double rate;

    public Currency() {
    }

    public Currency(String name, double rate) {
        setName(name);
        setRate(rate);
    }

    public void setId(int newId) {
        if (newId >= 0) {
            id = newId;
        } else {
            throw new IllegalArgumentException("Value < 0");
        }
    }

    public int getId() {
        return id;
    }

    public void setName(String newName) {
        if (newName != null && !newName.equals("")) {
            name = newName;
        } else {
            throw new IllegalArgumentException("String is null or empty");
        }
    }

    public String getName() {
        return name;
    }

    public void setRate(double newRate) {
        if (newRate > 0) {
            rate = newRate;
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Currency)) {
            return false;
        }
        Currency otherCurrency = (Currency) other;
        return this.id == otherCurrency.getId() &&
                this.name.equals(otherCurrency.getName());
    }

}