package com.platinabank.accounts;

import com.platinabank.accounts.constants.AccountsConstants;
import com.platinabank.accounts.controller.AccountsController;
import com.platinabank.accounts.dto.AccountDto;
import com.platinabank.accounts.dto.CustomerDto;
import com.platinabank.accounts.dto.ResponseDto;
import com.platinabank.accounts.service.impl.AccountsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ControllerLayerTest {

    @InjectMocks
    private AccountsController accountController;

    @Mock
    private AccountsServiceImpl accountsService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doCreateAccountWhenSuccess() {
        // Create a sample CustomerDto
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber(1234567890L); // Set other properties as needed

        // Mocking the service behavior
        doNothing().when(accountsService).createAccount(customerDto);

        // Call the controller method
        ResponseEntity<ResponseDto> response = accountController.createAccount(customerDto);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_201, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_201, response.getBody().getStatusMsg());
    }


    @Test
    public void doGetAccountDetailsWhenSuccess() {
        // Defining a sample valid mobile number
        long mobileNumber = 1234567890L; // Replace with a valid mobile number

        // Creating a sample AccountDto
        AccountDto accountDto = new AccountDto();
        // Set properties in accountDto as needed

        // Mocking the service behavior to return the sample AccountDto
        when(accountsService.getAccountDetails(mobileNumber)).thenReturn(accountDto);

        // Calling the controller method
        ResponseEntity<AccountDto> response = accountController.getAccountDetails(mobileNumber);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }


    @Test
    public void doUpdateAccountDetailsWhenSuccess() {
        // Creating a sample AccountDto for updating
        AccountDto accountDto = new AccountDto();
        // Setting properties in accountDto as needed

        // Mocking the service behavior to return true when updating the account
        when(accountsService.updateAccount(accountDto)).thenReturn(true);

        // Calling the controller method
        ResponseEntity<ResponseDto> response = accountController.updateAccountDetails(accountDto);

        // Assertions for a successful update
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_200, response.getBody().getStatusMsg());
    }


    @Test
    public void doUpdateAccountDetailsWhenFailure() {
        // Creating a sample AccountDto for updating
        AccountDto accountDto = new AccountDto();
        // Setting properties in accountDto as needed

        // Mocking the service behavior to return false when updating the account
        when(accountsService.updateAccount(accountDto)).thenReturn(false);

        // Calling the controller method
        ResponseEntity<ResponseDto> response = accountController.updateAccountDetails(accountDto);

        // Assertions for a failed update
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_500, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_500, response.getBody().getStatusMsg());
    }


    @Test
    public void doDeleteAccountDetailsWhenSuccess() {
        // Defining a sample customer ID
        int customerId = 123; // Replace with a valid customer ID

        // Mocking the service behavior to return true when deleting the account
        when(accountsService.deleteAccount(customerId)).thenReturn(true);

        // Calling the controller method
        ResponseEntity<ResponseDto> response = accountController.deleteAccountDetails(customerId);

        // Assertions for a successful delete
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_200, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_200, response.getBody().getStatusMsg());
    }


    @Test
    public void doDeleteAccountDetailsWhenFailure() {
        // Defining a sample customer ID
        int customerId = 123; // Replace with a valid customer ID

        // Mocking the service behavior to return false when deleting the account
        when(accountsService.deleteAccount(customerId)).thenReturn(false);

        // Calling the controller method
        ResponseEntity<ResponseDto> response = accountController.deleteAccountDetails(customerId);

        // Assertions for a failed delete
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_500, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_500, response.getBody().getStatusMsg());
    }

}
