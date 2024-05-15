package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.model.CustomerDto;
import aze.coders.springbootcrud.model.CustomersDtoResponse;
import aze.coders.springbootcrud.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCustomer(@RequestBody CustomerDto customerDto) {
         customerService.saveCustomer(customerDto);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer id) {
         customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable Integer id, @RequestParam String name) {
         customerService.updateCustomer(id, name);
    }

    @PatchMapping("/{id}")
    public void updateCustomerWithPatch(@PathVariable Integer id, @RequestParam String name) {
        customerService.updateCustomerWithPatch(id, name);
    }

}
