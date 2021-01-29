package com.coding.bank.system.controllers;

import com.coding.bank.system.account.AccountType;
import com.coding.bank.system.models.Account;
import com.coding.bank.system.models.Customer;
import com.coding.bank.system.payload.request.CustomerVo;
import com.coding.bank.system.payload.response.MessageResponse;
import com.coding.bank.system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(CustomerVo customerVo) {

        Customer c = new Customer();
        c.setUsername(customerVo.getUsername());
        c.setBankName(customerVo.getBankName());
        c.setCustomerFirstName(customerVo.getCustomerFirstName());
        c.setGetCustomerLastName(customerVo.getGetCustomerLastName());
        c.setMobileNumber(customerVo.getMobileNumber());
        c.setAddress(customerVo.getAddress());
        c.setEmailId(customerVo.getEmailId());
        c.setIfscCode(customerVo.getIfscCode());
        c.setIdentificationType(customerVo.getIdentificationType());

        Account account = new Account();
        account.setNumber(customerVo.getAccount().getNumber());
        account.setCustomer_id(customerVo.getAccount().getCustomer_id());
        account.setBalance(customerVo.getAccount().getBalance());
        account.setActive(customerVo.getAccount().isActive());
        account.setType(customerVo.getAccount().getType());

        Set<Account> accountSet = new HashSet<>();
        Optional<Customer> customer = customerRepository.findByUsername(customerVo.getUsername());
        if (customer.isPresent()) {
            accountSet = customer.get().getAccountList();
        }
        accountSet.add(customerVo.getAccount());
        c.setAccountList(accountSet);

        customerRepository.save(c);
        return ResponseEntity.ok(new MessageResponse("Customer created successfully!"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateKYCCustomer(CustomerVo customerVo) {

        Optional<Customer> customer = customerRepository.findByUsername(customerVo.getUsername());
        if (customer.isPresent()) {
            if (customerVo.getEmailId() != null) {
                customer.get().setEmailId(customerVo.getEmailId());
            }
            if (customerVo.getAddress() != null) {
                customer.get().setAddress(customerVo.getAddress());
            }
            if ((new Long(customerVo.getMobileNumber())) != null) {
                customer.get().setMobileNumber(customerVo.getMobileNumber());
            }
        }
        customerRepository.save(customer.get());
        return ResponseEntity.ok(new MessageResponse("KYC updated created successfully!"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(CustomerVo customerVo) {
        Optional<Customer> customer = customerRepository.findByUsername(customerVo.getUsername());
        customerRepository.delete(customer.get());
        return ResponseEntity.ok(new MessageResponse("Customer deleted successfully!"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCustomerDetail(CustomerVo customerVo) {
        Optional<Customer> customer = customerRepository.findByUsername(customerVo.getUsername());
        if (customer.isPresent()) {
            CustomerVo customerVo1 = new CustomerVo();
            customerVo1.setUsername(customer.get().getUsername());
            customerVo1.setAddress(customer.get().getAddress());
            customerVo1.setBankName(customer.get().getBankName());
            customerVo1.setEmailId(customer.get().getEmailId());
            customerVo1.setCustomerFirstName(customer.get().getCustomerFirstName());
            customerVo1.setGetCustomerLastName(customer.get().getGetCustomerLastName());
            customerVo1.setAccountSet(customer.get().getAccountList());
            customerVo1.setIdentificationType(customer.get().getIdentificationType());
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(CustomerVo customerVo) {
        Optional<Customer> customer = customerRepository.findByUsername(customerVo.getUsername());
        Map<AccountType, Double> doubleMap = new HashMap<>();
        if (customer.isPresent()) {
            Set<Account> accountSet = customer.get().getAccountList();
            for (Account account : accountSet) {
                if (customerVo.getAccountType().equals(AccountType.CURRENT)) {
                    doubleMap.put(AccountType.CURRENT, account.getBalance());
                }
                if (customerVo.getAccountType().equals(AccountType.LOAN)) {
                    doubleMap.put(AccountType.LOAN, account.getBalance());
                }
                if (customerVo.getAccountType().equals(AccountType.SALARY)) {
                    doubleMap.put(AccountType.SALARY, account.getBalance());
                }

                if (customerVo.getAccountType().equals(AccountType.SAVINGS)) {
                    doubleMap.put(AccountType.SAVINGS, account.getBalance());
                }
            }


        }
        return ResponseEntity.ok(doubleMap);
    }
}
