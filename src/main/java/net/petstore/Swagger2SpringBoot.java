package net.petstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableCaching
@SpringBootApplication
@EnableSwagger2
//@ComponentScan(basePackages = { "net.petstore.service" , "net.petstore.api" , "net.petstore.configuration" ,"org.modelmapper"})
public class Swagger2SpringBoot{

    public static void main(String[] args) {

        SpringApplication.run(Swagger2SpringBoot.class, args);
    }
}
