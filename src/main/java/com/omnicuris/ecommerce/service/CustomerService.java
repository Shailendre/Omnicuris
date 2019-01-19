package com.omnicuris.ecommerce.service;

import com.omnicuris.ecommerce.error.ServiceResponseException;
import com.omnicuris.ecommerce.model.customer.Address;
import com.omnicuris.ecommerce.model.customer.Customer;
import com.omnicuris.ecommerce.model.customer.CustomerSignupRequest;
import com.omnicuris.ecommerce.repository.AddressRepository;
import com.omnicuris.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private AddressRepository addressRepository;

  public void validateRequest(CustomerSignupRequest customerSignupRequest)
      throws ServiceResponseException {

    if (!ValidationUtil.validateEmail(customerSignupRequest.getEmailId())) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message("Invalid email " + customerSignupRequest.getEmailId());
    }

    if (!ValidationUtil.validateContact(customerSignupRequest.getContactNo())) {
      throw ServiceResponseException.status(HttpStatus.BAD_REQUEST)
          .message("Invalid contact number " + customerSignupRequest.getContactNo());
    }
  }

  public void addCustomer(CustomerSignupRequest customerSignupRequest) {

    Customer customer = new Customer();
    customer.setFName(customerSignupRequest.getFName());
    customer.setLName(customerSignupRequest.getLName());
    customer.setContact(customerSignupRequest.getContactNo());
    customer.setEmailId(customerSignupRequest.getEmailId());

    // save customer
    Customer savedCustomer = customerRepository.save(customer);

    customerSignupRequest
        .getAddress()
        .forEach(
            addr -> {
              Address address = new Address();
              address.setAddress(addr);
              address.setCustId(savedCustomer);
              // save address
              addressRepository.save(address);
            });
  }
}
