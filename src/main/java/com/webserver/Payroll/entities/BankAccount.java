package com.webserver.Payroll.entities;

import jakarta.persistence.*;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue String BankAccountNumber;
    private double balance;

    public BankAccount() {
        this.balance = 0;
    }

    public double getBalance() {
        return this.balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "BankAccountNumber=" + BankAccountNumber + " Balance=" + this.balance + '}';
    }
}
