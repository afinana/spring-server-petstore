package net.petstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = { "net.petstore.service", "net.petstore.api", "net.petstore.configuration", "net.petstore.security", "net.petstore.mapper" })
public class Swagger2SpringBoot{

    public static void main(String[] args) {

        SpringApplication.run(Swagger2SpringBoot.class, args);
    }
}
