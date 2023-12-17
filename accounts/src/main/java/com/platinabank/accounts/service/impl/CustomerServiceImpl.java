package com.platinabank.accounts.service.impl;

import com.platinabank.accounts.dto.AccountDto;
import com.platinabank.accounts.dto.CardDto;
import com.platinabank.accounts.dto.CustomerDetailsDto;
import com.platinabank.accounts.dto.LoanResponseDto;
import com.platinabank.accounts.exception.ResourceNotFoundException;
import com.platinabank.accounts.model.Account;
import com.platinabank.accounts.model.Customer;
import com.platinabank.accounts.repository.AccountsRepository;
import com.platinabank.accounts.repository.CustomerRepository;
import com.platinabank.accounts.service.ICustomerService;
import com.platinabank.accounts.service.client.CardsFeignClient;
import com.platinabank.accounts.service.client.LoansFeignClient;
import com.platinabank.accounts.util.mapper.AccountMapper;
import com.platinabank.accounts.util.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private CardsFeignClient cardsFeignClient;

    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto getCustomerDetails(long mobileNumber,String correlationId) {
        /**
         * Fetching customer with given mobile number
         * Throwing exception if not exists
         */
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        /**
         * Optional check for account presence in case data is manipulated or erased
         * Throwing exception if not exists
         */
        Account account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("Account","customerId",customer.getCustomerId()));

        /**
         * Creating customerdetails dto and setting account info
         */
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountsDto(account,new AccountDto()));

        /**
         * Fetching card and loan details using feign clients and setting
         * in the carddetails dto
         */
        ResponseEntity<List<CardDto>> cardDtolistResponseEntity = cardsFeignClient.getAllCardDetails(correlationId,mobileNumber);
        ResponseEntity<LoanResponseDto> loanResponseDtoResponseEntity = loansFeignClient.getTotalLoanDetails(correlationId,mobileNumber);

        if(cardDtolistResponseEntity!=null){
            customerDetailsDto.setCardDtoList(cardDtolistResponseEntity.getBody());
        }

        if(loanResponseDtoResponseEntity!=null){
            customerDetailsDto.setLoanResponseDto(loanResponseDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}
