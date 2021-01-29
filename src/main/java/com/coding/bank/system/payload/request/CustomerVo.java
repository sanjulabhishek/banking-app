package com.coding.bank.system.payload.request;

import com.coding.bank.system.account.AccountType;
import com.coding.bank.system.models.Account;
import com.coding.bank.system.models.IdentificationType;

import java.util.HashSet;
import java.util.Set;

public class CustomerVo {
    private String customerFirstName;
    private String getCustomerLastName;
    private String address;
    private long mobileNumber;
    private Account account;
    private String username;
    private Set<Account> accountSet = new HashSet<>();
    private AccountType accountType;

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
    }

    public Set<IdentificationType> getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(Set<IdentificationType> identificationType) {
        this.identificationType = identificationType;
    }

    private Set<IdentificationType> identificationType = new HashSet<>();

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getGetCustomerLastName() {
        return getCustomerLastName;
    }

    public void setGetCustomerLastName(String getCustomerLastName) {
        this.getCustomerLastName = getCustomerLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    private String emailId;
    private String bankName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String ifscCode;


}
