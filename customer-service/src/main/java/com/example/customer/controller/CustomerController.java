package com.example.customer.controller;

import com.example.customer.domain.Customer;
import com.example.customer.service.CustomerService;
import com.example.customer.webapi.CreateCustomerRequest;
import com.example.customer.webapi.CreateCustomerResponse;
import com.example.customer.webapi.FindCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
  public CustomerService customerService = new CustomerService();

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RequestMapping(value = "/customers", method = RequestMethod.POST)
  public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {

    Customer customer = customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getCreditLimit());

    return new CreateCustomerResponse(customer.getId(), customer.getName(), customer.getCreditLimit().getAmount());
  }

  @RequestMapping(path = "/customer/{customerId}", method = RequestMethod.GET)
  public FindCustomerResponse findCustomer(@PathVariable(name = "customerId") String customerId) throws Exception {

    Customer customer = customerService.findCustomer(Long.parseLong(customerId));
    if (customer != null)
      return new FindCustomerResponse( customer.getId(), customer.getName(), customer.getCreditLimit().getAmount().longValue());
    else
      throw new Exception("Can't find customer id(" + customerId + ")");
  }
}
