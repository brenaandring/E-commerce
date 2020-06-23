package com.brena.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        String gmail_email = System.getenv("GMAIL_EMAIL");
        System.out.println("gmail_email = " + gmail_email);
        String gmail_password = System.getenv("GMAIL_PASSWORD");
        System.out.println("gmail_password = " + gmail_password);

        SpringApplication.run(EcommerceApplication.class, args);
    }

}
