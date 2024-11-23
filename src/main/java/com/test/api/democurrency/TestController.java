package com.test.api.democurrency;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/hello")
    public String test() {
        System.out.println("Hi My name is Isaac");
        return "Hello World!";
    }
}
