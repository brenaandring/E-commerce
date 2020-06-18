package com.brena.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties ")
class EcommerceApplicationTests {

    @Test
    void contextLoads() {
    }

}
