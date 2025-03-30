package com.webserver.Payroll.repositories;

import com.webserver.Payroll.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

// Database for BankAccounts
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
