package com.platinabank.accounts.service;

import com.platinabank.accounts.dto.AccountDto;
import com.platinabank.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     * @param customerDto - CustomerDto object
     */
    void createAccount(CustomerDto customerDto);

    boolean updateAccount(AccountDto accountDto);

    AccountDto getAccountDetails(Long mobileNumber);

    boolean deleteAccount(int customerId);

    void updateCommunicationStatus(Long accountNumber);

}
