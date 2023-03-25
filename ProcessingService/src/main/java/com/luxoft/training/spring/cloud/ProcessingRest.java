package com.luxoft.training.spring.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ProcessingRest {
    private final AccountServiceClient accountServiceClient;
    private final CardServiceClient cardServiceClient;
    private final ProcessingRepository processingRepository;

    public ProcessingRest(@Qualifier("com.luxoft.training.spring.cloud.AccountServiceClient") AccountServiceClient accountServiceClient,
                          @Qualifier("com.luxoft.training.spring.cloud.CardServiceClient") CardServiceClient cardServiceClient, ProcessingRepository processingRepository) {
        this.accountServiceClient = accountServiceClient;
        this.cardServiceClient = cardServiceClient;
        this.processingRepository = processingRepository;
    }

    @GetMapping("/get-account/{id}")
    public String getAccountById(@PathVariable(name = "id") Integer id) {
        return accountServiceClient.getById(id);
    }

    @GetMapping("/issue/{id}")
    public ProcessingEntity generateCard(@PathVariable(name = "id") Integer id) {
        String byId = accountServiceClient.getById(id);
        if (Objects.isNull(byId)) return null;
        String cardNumber = cardServiceClient.generateCardNumber();
        return processingRepository.save(new ProcessingEntity(cardNumber, id));
    }

    @GetMapping("/get")
    public List<String> getCardNumberByAccountId(@RequestParam(name = "account_id") Integer id) {
        List<ProcessingEntity> byAccountIdIn = processingRepository.findByAccountIdIn(Collections.singletonList(id));
        return byAccountIdIn.stream().map(ProcessingEntity::getCard).collect(Collectors.toList());
    }

    @GetMapping("/checkout/{cardnumber}")
    public Boolean spendSomeMoney(@PathVariable(value = "cardnumber") String cardNumber, @RequestParam(value = "sum") BigDecimal sum) {
        ProcessingEntity byCardNum = processingRepository.findByCard(cardNumber);
        if (Objects.isNull(byCardNum)) return null;
        return accountServiceClient.withdrawn(byCardNum.getAccountId(), sum);
    }

    @HystrixCommand(fallbackMethod = "testFallback")
    @GetMapping("/test")
    public String testHystrix(@RequestParam(name = "fail") Boolean fail) {
        if (Boolean.TRUE.equals(fail)) {
            throw new RuntimeException();
        }
        return "OK";
    }

    private String testFallback(Boolean fail) {
        return "FAILED";
    }
}
