package aze.coders.springbootcrud.service;

import aze.coders.springbootcrud.entity.Customer;
import aze.coders.springbootcrud.exception.CUSTOMER_NOT_FOUND_EXCEPTION;
import aze.coders.springbootcrud.model.CustomerDto;
import aze.coders.springbootcrud.model.CustomersDtoResponse;
import aze.coders.springbootcrud.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomersDtoResponse getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return new CustomersDtoResponse(customers.stream().map(this::convertEntityToDto).collect(Collectors.toList()));
    }

    @Override
    public CustomerDto getCustomer(Integer id) {
        return convertEntityToDto(findById(id));
    }

    @Override
    public CustomersDtoResponse getCustomerByName(String name) {
        List<Customer> customers = customerRepository.findByName(name);
        return new CustomersDtoResponse(customers.stream().map(this::convertEntityToDto).collect(Collectors.toList()));
    }

    @Override
    public void deleteCustomer(Integer id) {
        findById(id);
        customerRepository.deleteById(id);
    }

    @Override
    public void saveCustomer(CustomerDto customerDto) {
        customerRepository.save(convertDtoToEntity(customerDto));
    }

    @Override
    public void updateCustomer(Integer id, String name) {
        Customer customer = new Customer();
        customer.setName(name);
        findById(id);
        customer.setId(id);
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomerWithPatch(Integer id, String name) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setId(id);
        customerRepository.save(customer);
    }

    private Customer convertDtoToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

    private CustomerDto convertEntityToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }

    private Customer findById(Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new CUSTOMER_NOT_FOUND_EXCEPTION("Customer not found"));
    }
}
