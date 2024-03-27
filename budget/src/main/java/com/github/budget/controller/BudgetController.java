package com.github.budget.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class BudgetController {

    @GetMapping("/myBudget")
    public String getBudgetDetails() {
        return "My budget details: personal, necessities, rent, savings and emergency.";
    }

    @GetMapping("/monthly")
    public String monthlyBudget() {
        return "Remaining budget for the month: $5000.00";
    }

}
