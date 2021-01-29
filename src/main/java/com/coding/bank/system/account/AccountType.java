package com.coding.bank.system.account;

/**
 *
 */
public enum AccountType {

    SAVINGS("SAVINGS"),
    LOAN("LOAN"),
    SALARY("SALARY"),
    CURRENT("CURRENT");

    private final String accountType;

    AccountType(String account) {
        accountType = account;
    }

    public String getAccountType() {
        return accountType;
    }
}
