package com.coding.bank.system.models;

import com.coding.bank.system.account.AccountType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String customerFirstName;
	private String getCustomerLastName;
	private String address;
	private long mobileNumber;
	private String emailId;
	private String bankName;
	private String ifscCode;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<IdentificationType> identificationType = new HashSet<>();

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="CUSTOMER_ID")
	private Set<Account> accountList = new HashSet<>();

	public Customer(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Set<IdentificationType> getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(Set<IdentificationType> identificationType) {
		this.identificationType = identificationType;
	}

	public Set<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(Set<Account> accountList) {
		this.accountList = accountList;
	}


	public static void main(String[] args) throws JsonProcessingException {
		Customer c = new Customer();
		c.setMobileNumber(123456);
		c.setEmailId("test@test1.com");
		c.setCustomerFirstName("AA");
		c.setGetCustomerLastName("CC");
		c.setUsername("user1");
		c.setAddress("add");
		IdentificationType identificationType = new IdentificationType();
		identificationType.setName("Passport");
		identificationType.setAddress("address");
		identificationType.setCountry("India");
		identificationType.setExpiry("2029-07-02");
		identificationType.setValid(true);
		identificationType.setNumber("A-111");
		Set<IdentificationType> identificationTypeSet = new HashSet<>();
		identificationTypeSet.add(identificationType);
		c.setIdentificationType(identificationTypeSet);

		Account account = new Account();
		account.setNumber(12345);
		account.setType(AccountType.SALARY);
		account.setActive(true);
		account.setBalance(20000);

		Set<Account> accountSet = new HashSet<>();
		accountSet.add(account);
		c.setAccountList(accountSet);

		ObjectMapper objectMapper = new ObjectMapper();
		String ss = objectMapper.writeValueAsString(c);
		System.out.println(ss);
	}
}
