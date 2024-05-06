package aze.coders.springbootcrud.repository;

import aze.coders.springbootcrud.entity.Customer;
import aze.coders.springbootcrud.model.CustomerDto;
import aze.coders.springbootcrud.model.CustomersDtoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByName(String name);
}
