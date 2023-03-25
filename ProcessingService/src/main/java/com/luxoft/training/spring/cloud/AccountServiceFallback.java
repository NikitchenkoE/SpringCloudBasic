package com.luxoft.training.spring.cloud;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountServiceFallback implements AccountServiceClient{

    @Override
    public String create(Integer id) {
        return "Not Created";
    }

    @Override
    public String getById(Integer id) {
        return "Fallback Response";
    }

    @Override
    public boolean fund(Integer id, BigDecimal sum) {
        return false;
    }

    @Override
    public boolean withdrawn(Integer id, BigDecimal sum) {
        return false;
    }
}
