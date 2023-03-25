package com.luxoft.training.spring.cloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardRest {
    private final CardNumberGenerator cardNumberGenerator;

    public CardRest(CardNumberGenerator cardNumberGenerator) {
        this.cardNumberGenerator = cardNumberGenerator;
    }

    @GetMapping("/create")
    public String generateCardNumber() {
        return cardNumberGenerator.generate();
    }
}
