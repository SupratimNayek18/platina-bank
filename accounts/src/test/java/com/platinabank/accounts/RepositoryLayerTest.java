package com.platinabank.accounts;

import com.platinabank.accounts.model.Customer;
import com.platinabank.accounts.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class RepositoryLayerTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindByMobileNumber() {
        // Create a customer and save it to the database
        Customer customer = new Customer();
        customer.setMobileNumber(1234567890L);
        customerRepository.save(customer);

        // Perform the findByMobileNumber operation
        Optional<Customer> foundCustomer = customerRepository.findByMobileNumber(1234567890L);

        // Assert that the customer is found
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getMobileNumber()).isEqualTo(1234567890L);
        customerRepository.delete(customer);
    }

}
