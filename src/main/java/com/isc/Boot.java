package com.isc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackageClasses = Boot.class)
@RestController("/")
public class Boot {

    public static void main(String[] args) {
        SpringApplication.run(Boot.class);
    }



    @RequestMapping("")
    public String helloWorld() {
        return "hello world";
    }


}
