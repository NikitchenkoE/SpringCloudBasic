package com.luxoft.training.spring.cloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "AccountService", fallback = AccountServiceFallback.class)
public interface AccountServiceClient {

    @GetMapping("/create")
    String create(@RequestParam(name = "client_id") Integer id);

    @GetMapping("/get/{id}")
    String getById(@PathVariable(name = "id") Integer id);

    @GetMapping("/fund/{id}")
    boolean fund(@PathVariable(name = "id") Integer id, @RequestParam(name = "sum") BigDecimal sum);

    @GetMapping("/checkout/{id}")
    boolean withdrawn(@PathVariable(name = "id") Integer id, @RequestParam(name = "sum") BigDecimal sum);
}
