package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.model.CustomerDto;
import aze.coders.springbootcrud.model.CustomersDtoResponse;
import aze.coders.springbootcrud.service.CustomerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Customer crud services")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomersDtoResponse> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDto customerDto) {
        if (customerDto.getName().length() > 10)
            return ResponseEntity.ok(customerService.saveCustomer(customerDto));
        else
            return ResponseEntity.ofNullable("Customer name is too long");
    }

    @GetMapping("/{id}")
    @Operation(summary = "get By id", description = "This method find customer by id")
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

    @PostMapping("/post")
    public void transactionCustomer(@RequestBody CustomerDto customerDto) {
        customerService.saveCustomer(customerDto);
    }


}
