package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.model.CustomerDto;
import aze.coders.springbootcrud.model.CustomersDtoResponse;
import aze.coders.springbootcrud.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public CustomersDtoResponse getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Integer id) {
        return customerService.getCustomer(id);
    }

    @GetMapping("/search")
    public CustomersDtoResponse getCustomerByName(@RequestParam String name) {
        return customerService.getCustomerByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
         customerService.deleteCustomer(id);
    }
}
