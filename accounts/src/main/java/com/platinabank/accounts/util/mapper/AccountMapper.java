package com.platinabank.accounts.util.mapper;

import com.platinabank.accounts.dto.AccountDto;
import com.platinabank.accounts.model.Account;

public class AccountMapper {

    private AccountMapper(){}

    public static AccountDto mapToAccountsDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        accountDto.setAmount(account.getAmount());
        return accountDto;
    }

    public static Account mapToAccounts(AccountDto accountDto, Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        if(accountDto.getAccountType()!=null) {
            account.setAccountType(accountDto.getAccountType());
        }
        if(accountDto.getBranchAddress()!=null){
            account.setBranchAddress(accountDto.getBranchAddress());
        }
        if(accountDto.getAmount()!=0.0){
            account.setAmount(accountDto.getAmount());
        }
        return account;
    }

}
