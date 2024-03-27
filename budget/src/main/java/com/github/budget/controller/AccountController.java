package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AccountController {

    @GetMapping("/myAccount")
    public String getAccountDetails() {
        return "My account details: bank account, credit card, debit card, savings account and emergency fund.";
    }

}
