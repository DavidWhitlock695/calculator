package com.example.calculator.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class BusinessRuleViolationException extends RuntimeException {
    private final BusinessRuleTypes businessRuleType;
    private final Map<String, String> violationValues;

    public BusinessRuleViolationException(BusinessRuleTypes businessRuleType) {
        super(businessRuleType.getMessage());
        this.businessRuleType = businessRuleType;
        this.violationValues = Map.of();
    }

    public BusinessRuleViolationException(BusinessRuleTypes businessRuleType, Map<String, String> violationValues) {
        super(businessRuleType.getMessage());
        this.businessRuleType = businessRuleType;
        this.violationValues = violationValues;
    }
}

