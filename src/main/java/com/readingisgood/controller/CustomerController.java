package com.readingisgood.controller;

import com.readingisgood.exception.ErrorCodeEnum;
import com.readingisgood.exception.ReadingIsGoodApiException;
import com.readingisgood.models.Customer;
import com.readingisgood.models.CustomerDTO;
import com.readingisgood.services.CustomerService;
import com.readingisgood.services.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    private final ModelMapper modelMapper;


    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        try {
            Customer customer = modelMapper.map(customerDTO, Customer.class);
            Customer createdCustomer = customerService.createCustomer(customer);
            CustomerDTO createdCustomerDTO = modelMapper.map(createdCustomer, CustomerDTO.class);
            LOGGER.info("The Customer : {} has been successfully registered.", createdCustomerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(createdCustomerDTO);
        } catch (IllegalArgumentException e) {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.CUSTOMER_ILLEGAL_ARGUMENT_ERROR);
        }

    }


    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = modelMapper.map(customer.get(), CustomerDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        } else {
            throw new ReadingIsGoodApiException(ErrorCodeEnum.CUSTOMER_NOT_FOUND);
        }

    }

    @GetMapping("/get-all-customer")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomer(@RequestParam int pageIndex, @RequestParam int pageSize) {
        Page<CustomerDTO> allCustomer = customerService.getAllCustomer(pageIndex, pageSize)
                .map(entity -> modelMapper.map(entity, CustomerDTO.class));
        return ResponseEntity.status(HttpStatus.OK).body(allCustomer);
    }

}
