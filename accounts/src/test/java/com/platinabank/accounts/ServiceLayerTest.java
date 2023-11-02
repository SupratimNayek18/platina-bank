package com.platinabank.accounts;

import com.platinabank.accounts.dto.AccountDto;
import com.platinabank.accounts.dto.CustomerDto;
import com.platinabank.accounts.exception.CustomerAlreadyExistsException;
import com.platinabank.accounts.exception.ResourceNotFoundException;
import com.platinabank.accounts.model.Account;
import com.platinabank.accounts.model.Customer;
import com.platinabank.accounts.repository.AccountsRepository;
import com.platinabank.accounts.repository.CustomerRepository;
import com.platinabank.accounts.service.impl.AccountsServiceImpl;
import com.platinabank.accounts.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceLayerTest {

    @Mock
    AccountsRepository accountsRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    IdGenerator idGenerator;

    @InjectMocks
    AccountsServiceImpl accountsService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doCreateAccountWhenCustomerDoesNotExist(){

        // Creating a sample customerDto
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber(7894561236L);

        // Creating a customer that is not present in the repository
        when(customerRepository.findByMobileNumber(7894561236L)).thenReturn(Optional.empty());

        // Mocking the behavior of IdGenerator to return a known customer and account ID
        when(idGenerator.generateCustomerId()).thenReturn(123456);
        when(idGenerator.generateAccountId()).thenReturn(123456789123L);

        //Mocking the behaviour of customerRepo
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(new Customer());

        accountsService.createAccount(customerDto);

        //Verify if findByMobileNumber return nothing
        verify(customerRepository).findByMobileNumber(7894561236L);

        //Verify of save methods of both repositories were called
        verify(customerRepository).save(Mockito.any(Customer.class));
        verify(accountsRepository).save(Mockito.any(Account.class));

    }


    @Test
    public void doCreateAccountWhenCustomerExists() {
        // Creating a sample customerDto
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber(7894561236L);

        // Creating a customer that already exists in the repository
        when(customerRepository.findByMobileNumber(7894561236L)).thenReturn(Optional.of(new Customer()));

        // Calling the method and expect a CustomerAlreadyExistsException
        assertThrows(CustomerAlreadyExistsException.class, () -> accountsService.createAccount(customerDto));

        // Verify that the customerRepository and accountsRepository methods were not called
        verify(customerRepository, never()).save(Mockito.any(Customer.class));
        verify(accountsRepository, never()).save(Mockito.any(Account.class));
    }


    @Test
    public void doUpdateAccountWhenSuccessfulUpdate() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(123); // Account exists

        Account account = new Account();
        when(accountsRepository.findById(123L)).thenReturn(Optional.of(account));

        boolean isUpdated = accountsService.updateAccount(accountDto);

        assertTrue(isUpdated);
        verify(accountsRepository).save(account);
    }


    @Test
    public void testUpdateAccountWhenSuccessfulAccountAndCustomerUpdate() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(123); // Account exists

        Account account = new Account();
        when(accountsRepository.findById(123L)).thenReturn(Optional.of(account));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber(1234567896L);
        account.setCustomerId(456); // Customer exists

        Customer customer = new Customer();
        when(customerRepository.findById(456)).thenReturn(Optional.of(customer));

        accountDto.setCustomerDto(customerDto);

        boolean isUpdated = accountsService.updateAccount(accountDto);

        assertTrue(isUpdated);
        verify(accountsRepository).save(account);
        verify(customerRepository).save(customer);
    }


    @Test
    public void doUpdateAccountWhenAccountNotFound() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(123); // Account does not exist

        when(accountsRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountsService.updateAccount(accountDto));
        verify(accountsRepository, never()).save(any());
    }


    @Test
    public void doUpdateAccountWhenCustomerNotFound() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(123); // Account exists

        Account account = new Account();
        when(accountsRepository.findById(123L)).thenReturn(Optional.of(account));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber(1234567893L);
        account.setCustomerId(456); // Customer does not exist

        when(customerRepository.findById(456)).thenReturn(Optional.empty());

        accountDto.setCustomerDto(customerDto);

        assertThrows(ResourceNotFoundException.class, () -> accountsService.updateAccount(accountDto));
        verify(customerRepository, never()).save(any());
    }


    @Test
    public void doGetAccountWhenCustomerExists(){

        Customer customer = new Customer();
        customer.setCustomerId(123456);
        customer.setMobileNumber(1234567893L);

        Account account = new Account();
        account.setCustomerId(123456);

        when(customerRepository.findByMobileNumber(1234567893L)).thenReturn(Optional.of(customer));
        when(accountsRepository.findByCustomerId(123456)).thenReturn(Optional.of(account));

        AccountDto accountDto = accountsService.getAccountDetails(1234567893L);

        assertNotNull(accountDto);
        assertEquals(customer.getMobileNumber(),accountDto.getCustomerDto().getMobileNumber());

    }


    @Test
    public void testGetAccountDetailsWhenCustomerNotFound() {
        // Mocking the repository behavior to simulate customer not found
        Mockito.when(customerRepository.findByMobileNumber(1234567893L)).thenReturn(Optional.empty());

        // Expecting a ResourceNotFoundException when calling the method
        assertThrows(ResourceNotFoundException.class, () -> accountsService.getAccountDetails(1234567893L));
    }


    @Test
    public void testGetAccountDetailsWhenAccountNotFound() {
        // Creating a sample customer
        Customer customer = new Customer();
        customer.setCustomerId(123456);
        customer.setMobileNumber(1234567893L);

        // Mocking the repository behavior to simulate account not found
        Mockito.when(customerRepository.findByMobileNumber(1234567893L)).thenReturn(Optional.of(customer));
        Mockito.when(accountsRepository.findByCustomerId(123456)).thenReturn(Optional.empty());

        // Expecting a ResourceNotFoundException when calling the method
        assertThrows(ResourceNotFoundException.class, () -> accountsService.getAccountDetails(1234567890L));
    }


    @Test
    public void doDeleteAccountWhenCustomerExists(){
        Customer customer = new Customer();
        customer.setCustomerId(123456);

        when(customerRepository.findById(123456)).thenReturn(Optional.of(customer));

        boolean result = accountsService.deleteAccount(123456);

        verify(customerRepository).delete(any());
        verify(accountsRepository).deleteByCustomerId(anyInt());
        assertTrue(result);
    }


    @Test
    public void doDeleteAccountWhenCustomerDoesNotExist(){
        Customer customer = new Customer();
        customer.setCustomerId(123456);

        when(customerRepository.findById(123456)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->accountsService.deleteAccount(123456));
        verify(customerRepository, never()).delete(any());
        verify(accountsRepository, never()).deleteByCustomerId(anyInt());
    }


}
