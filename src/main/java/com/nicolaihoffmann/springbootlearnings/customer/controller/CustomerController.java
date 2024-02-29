package com.nicolaihoffmann.springbootlearnings.customer.controller;

import com.nicolaihoffmann.springbootlearnings.customer.dto.CustomerDTO;
import com.nicolaihoffmann.springbootlearnings.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final SecurityContextHolder securityContextHolder;

  /*  @Autowired
    public CustomerController(CustomerService customerService, ) {
        this.customerService = customerService;
    }
*/
    //@Secured()
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        //Permissionpruefer.darfLesen("CUSTROMER_XXX_KEY")//.orElse(throw new RuntimeException(403))
        securityContextHolder.
        return customerService.getAllCustomer();
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<CustomerDTO> getCustomersByExternalId(@PathVariable UUID externalId) {
        CustomerDTO customerDTO = customerService.getCustomerById(externalId);
        if (customerDTO != null) {
            return ResponseEntity.ok(customerDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomers(@RequestBody CustomerDTO customer) {
        if (customer.getExternalId() != null) {
            throw new IllegalArgumentException("ExternalId should not be provided in the request body for new customers.");
        }
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{externalId}")
    public CustomerDTO updateCustomers(@PathVariable UUID externalId, @RequestBody CustomerDTO newCustomer) {
        return customerService.updateCustomer(externalId, newCustomer);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Object> deleteCustomers(@PathVariable UUID externalId) {
        customerService.deleteCustomer(externalId);
        return ResponseEntity.noContent().build();
    }
}
