package com.luxoft.training.spring.cloud;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class AccountDAO {
    private static final Lock balanceLock = new ReentrantLock();

    private final AccountRepository repo;

    public AccountDAO(AccountRepository repo) {
        this.repo = repo;
    }

    public AccountEntity create(Integer clientId) {
        AccountEntity account = new AccountEntity();
        account.setClientId(clientId);
        account.setBalance(BigDecimal.ZERO);
        return repo.save(account);
    }

    public boolean addBalance(Integer id, BigDecimal balance) {
        balanceLock.lock();
        try {
            AccountEntity account = repo.findById(id).orElse(null);
            if (account != null) {
                account.setBalance(account.getBalance().add(balance));
                if (account.getBalance().compareTo(BigDecimal.ZERO) >= 0) {
                    repo.save(account);
                    return true;
                }
            }
        } finally {
            balanceLock.unlock();
        }
        return false;
    }

    public boolean withdrawnMoney(Integer id, BigDecimal sum) {
        balanceLock.lock();
        try {
            AccountEntity account = repo.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Account with id %s", id)));

            if (account.getBalance().compareTo(sum) >= 0 && sum.compareTo(BigDecimal.ZERO) >= 0) {
                account.setBalance(account.getBalance().subtract(sum));
                repo.save(account);
                return true;
            } else throw new RuntimeException("Not enough money");
        } finally {
            balanceLock.unlock();
        }
    }

    public AccountEntity getInfo(Integer id) {
        return repo.findById(id).orElse(null);
    }
}
