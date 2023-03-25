package com.luxoft.training.spring.cloud;

import org.springframework.stereotype.Component;

@Component
public class CardServiceFallback implements CardServiceClient{
    @Override
    public String generateCardNumber() {
        return "00000000000000000";
    }
}
