package aze.coders.springbootcrud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
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
}
