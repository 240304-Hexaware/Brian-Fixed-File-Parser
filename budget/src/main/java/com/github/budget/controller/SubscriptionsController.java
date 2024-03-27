package com.github.budget.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionsController {

    @GetMapping("/mySubscriptions")
    public String getSubscriptions() {
        return "My subscriptions: Udemy, Amazon Prime, Walmart+, Copilot and Apple Music.";
    }

}
