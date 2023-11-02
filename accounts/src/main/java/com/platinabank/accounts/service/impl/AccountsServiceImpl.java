package com.platinabank.accounts.service.impl;

import com.platinabank.accounts.constants.AccountsConstants;
import com.platinabank.accounts.dto.AccountDto;
import com.platinabank.accounts.dto.CustomerDto;
import com.platinabank.accounts.exception.CustomerAlreadyExistsException;
import com.platinabank.accounts.exception.ResourceNotFoundException;
import com.platinabank.accounts.model.Account;
import com.platinabank.accounts.model.Customer;
import com.platinabank.accounts.repository.AccountsRepository;
import com.platinabank.accounts.repository.CustomerRepository;
import com.platinabank.accounts.service.IAccountsService;
import com.platinabank.accounts.util.IdGenerator;
import com.platinabank.accounts.util.mapper.AccountMapper;
import com.platinabank.accounts.util.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private IdGenerator idGenerator;

    /**
     * Method to register new customer
     * @param customerDto - CustomerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto){

        //Throwing exception if customer mobile number already exists in the system
        Optional<Customer> fetchedCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(fetchedCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer with mobile number "+customerDto.getMobileNumber()+" already exists");
        }

        //Mapping the customerDto to customer model
        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());

        //Generating customerId for customer model and saving the model into the repository
        customer.setCustomerId(idGenerator.generateCustomerId());
        Customer savedCustomer = customerRepository.save(customer);

        //Creating the account model with the customer id from saved customer
        Account account = new Account();
        account.setAccountNumber(idGenerator.generateAccountId());
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setCustomerId(savedCustomer.getCustomerId());
        account.setAmount(0);
        account.setCreatedDate(LocalDateTime.now());
        account.setBranchAddress(AccountsConstants.ADDRESS);

        //Saving the account model
        accountsRepository.save(account);

    }

    //Method to update account details
    @Override
    public boolean updateAccount(AccountDto accountDto) {

        boolean isUpdated = false;

        if(accountDto.getAccountNumber()!=0){
            //If account number exists updating and saving the data else throwing exception
            Account account = accountsRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(()-> new ResourceNotFoundException("Account","accountNumber",accountDto.getAccountNumber()));
            account = AccountMapper.mapToAccounts(accountDto,account);
            accountsRepository.save(account);

            //Checking if customer dto exists in the accountDto received as argument
            if(accountDto.getCustomerDto()!=null){
                //Updating customer information and saving in the db
                int customerId = account.getCustomerId();
                Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(()->new ResourceNotFoundException("Customer","customerId",customerId));
                customer = CustomerMapper.mapToCustomer(accountDto.getCustomerDto(),customer);
                customerRepository.save(customer);
            }
            isUpdated = true;
        }

        return isUpdated;
    }

    //Method to get account details
    @Override
    public AccountDto getAccountDetails(Long mobileNumber) {
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

        //Setting customer information in account dto and returning the same
        AccountDto accountDto = AccountMapper.mapToAccountsDto(account,new AccountDto());
        accountDto.setCustomerDto(CustomerMapper.mapToCustomerDto(customer,new CustomerDto()));

        return accountDto;
    }

    //Method to delete account
    @Override
    public boolean deleteAccount(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->new ResourceNotFoundException("Customer","customerId",customerId));
        customerRepository.delete(customer);
        accountsRepository.deleteByCustomerId(customerId);
        return true;
    }

}
