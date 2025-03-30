package com.webserver.Payroll;

import java.util.Optional;

import com.webserver.Payroll.entities.BankAccount;
import com.webserver.Payroll.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class PayRollController {

    private BankAccountRepository bankAcountRepository;


    public PayRollController(BankAccountRepository bankAcountRepository) {
        this.bankAcountRepository = bankAcountRepository;
    }
    // Create a bankAccount
    @PostMapping("/createAccount")
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        BankAccount account = bankAcountRepository.save(bankAccount);
        return ResponseEntity.ok(account);
    }
    // Get account balance by account number
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<Double> getBalance(@PathVariable String accountNumber) {
         return bankAcountRepository.findById(accountNumber)
                 .map(bankAccount -> ResponseEntity.ok(bankAccount.getBalance()))
                 .orElse(ResponseEntity.notFound().build());
    }
    // Deposit money into the bank account
    // @Transactional because were working with "money" and if something goes wrong it will be rolled back automatically to previous state
    @PutMapping("/deposit/{accountNumber}")
    @Transactional
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be positive");
        }
        Optional<BankAccount> account = bankAcountRepository.findById(accountNumber);
        if (account.isPresent()) {
            BankAccount bankAccount = account.get();
            bankAccount.updateBalance(amount);
            bankAcountRepository.save(bankAccount);
            return ResponseEntity.ok("Successfully deposited " + amount + " euros! New balance: " + bankAccount.getBalance());
        }
        return ResponseEntity.notFound().build();
    }
    // Withdraw money from the bank account
    // @Transactional because were working with "money" and if something goes wrong it will be rolled back automatically to previous state
    @PutMapping("/withdraw/{accountNumber}")
    @Transactional
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be positive");
        }
        Optional<BankAccount> account = bankAcountRepository.findById(accountNumber);
        if (account.isPresent()) {
            BankAccount bankAccount = account.get();
            // Needs to be synchronized because maybe 2 withdrawals can be too low for our balance.
            synchronized (bankAccount) {
                if (bankAccount.getBalance() < amount) {
                    return ResponseEntity.badRequest().body("Insufficient funds");
                }
                bankAccount.updateBalance(-amount);
                bankAcountRepository.save(bankAccount);
                return ResponseEntity.ok("Successfully withdrawn " + amount + " euros! New balance: " + bankAccount.getBalance());
            }
        }
        return ResponseEntity.notFound().build();
    }
    // Delete a bankAccount
    @DeleteMapping("/deleteAccount/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        Optional<BankAccount> account = bankAcountRepository.findById(accountNumber);
        if (account.isPresent()) {
            bankAcountRepository.delete(account.get());
            return ResponseEntity.ok("Successfully deleted bankAccount! BankAccountNumber:" + accountNumber);
        }
        return ResponseEntity.notFound().build();
    }
}
