package com.platinabank.accounts.util.mapper;

import com.platinabank.accounts.dto.CustomerDetailsDto;
import com.platinabank.accounts.dto.CustomerDto;
import com.platinabank.accounts.model.Customer;

public class CustomerMapper {

    private CustomerMapper(){}

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setFirstName(customer.getFirstName());
        customerDetailsDto.setLastName(customer.getLastName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        if(customerDto.getFirstName()!=null){
            customer.setFirstName(customerDto.getFirstName());
        }
        if(customerDto.getLastName()!=null){
            customer.setLastName(customerDto.getLastName());
        }
        if(customerDto.getEmail()!=null){
            customer.setEmail(customerDto.getEmail());
        }
        if(customerDto.getMobileNumber()!=0){
            customer.setMobileNumber(customerDto.getMobileNumber());
        }
        return customer;
    }

}
