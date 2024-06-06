package org.marv.services;

import jakarta.transaction.Transactional;
import org.marv.repository.AccountRepository;
import org.marv.responses.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.marv.entities.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO generateAccount(String username, String id) {
        if (accountRepository.existsByUsername(username)) {
            throw new IllegalStateException("Username already exists");
        }
        Account newAccount = new Account(username, id);
        accountRepository.save(newAccount);

        AccountDTO accountDTO = new AccountDTO(
                newAccount.getId(),
                newAccount.getUsername(),
                newAccount.isActive()
        );

        return accountDTO;
    }
    @Transactional
    public AccountDTO deactivateAccount(String accountId) {
        Optional<Account> accOpt = accountRepository.findById(accountId);
        if (accOpt.isPresent()) {
            Account acc = accOpt.get();
            acc.setActive(false);
            accountRepository.save(acc);


            AccountDTO accDTO = new AccountDTO(
                    acc.getId(),
                    acc.getUsername(),
                    acc.isActive()
            );
            return accDTO;
        } else {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
    }

    @Transactional
    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }
}
