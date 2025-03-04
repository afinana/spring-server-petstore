package net.petstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class Swagger2SpringBoot{

    public static void main(String[] args) {

        SpringApplication.run(Swagger2SpringBoot.class, args);
    }
}
