package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CardsController {
    @GetMapping("/myCards")
    public String getCardDetails() {
        return "My card details: credit card, debit card, prepaid card, secured card and virtual card.";
    }

}
