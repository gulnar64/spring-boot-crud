package aze.coders.springbootcrud.controller;

import aze.coders.springbootcrud.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;
    @GetMapping("/public")
    public String sayHelloPublic() {
        return "Hello public";
    }

    @GetMapping("/auth")
    public String sayHelloAuth() {
        return "Hello auth";
    }

    @GetMapping("/user")
    public String sayHelloUser() {
        return "Hello user";
    }

    @GetMapping("/admin")
    public String sayHelloAdmin() {
        return "Hello admin";
    }

    @GetMapping("/method-admin")
    public String sayHelloMethodAdmin() {
        return helloService.sayHelloMethodAdmin() ;
    }
}
