package aze.coders.springbootcrud.service.impl;

import aze.coders.springbootcrud.service.HelloService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    public String sayHelloMethodAdmin() {
        return "Hello method admin";
    }
}
