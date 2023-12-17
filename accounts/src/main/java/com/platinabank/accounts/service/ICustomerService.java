package com.platinabank.accounts.service;

import com.platinabank.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto getCustomerDetails(long mobileNumber,String correlationId);

}
