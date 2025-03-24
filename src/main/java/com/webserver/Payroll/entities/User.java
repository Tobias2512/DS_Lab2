package com.webserver.Payroll.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class User {
    private @Id
    @GeneratedValue Long id;
    private String name;
    private double balance;

    public User() {}

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.balance);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", balance=" + balance + "]";
    }
}
