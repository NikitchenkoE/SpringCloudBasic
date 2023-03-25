package com.luxoft.training.spring.cloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountRest {
    private final AccountDAO accountDAO;

    public AccountRest(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @GetMapping("/get/{id}")
    public AccountEntity getById(@PathVariable(name = "id") Integer id) {
        System.out.println(String.format("Get account by id %s", id));
        return accountDAO.getInfo(id);
    }

    @GetMapping("/create")
    public AccountEntity create(@RequestParam(name = "client_id") Integer id) {
        return accountDAO.create(id);
    }

    @GetMapping("/fund/{id}")
    public boolean fund(@PathVariable(name = "id") Integer id, @RequestParam(name = "sum") BigDecimal sum) {
        return accountDAO.addBalance(id, sum);
    }

    @GetMapping("/checkout/{id}")
    public boolean withdrawn(@PathVariable(name = "id") Integer id, @RequestParam(name = "sum") BigDecimal sum) {
        return accountDAO.withdrawnMoney(id, sum);
    }
}
