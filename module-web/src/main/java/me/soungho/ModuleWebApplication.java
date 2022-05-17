package me.soungho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ModuleWebApplication {
    public static void main(String[] args){
        SpringApplication.run(ModuleWebApplication.class, args);
    }

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "hello world";
    }
}
