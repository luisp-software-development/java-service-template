package com.luispdev.javaservicetemplate.domain;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@SpringBootTest
public class AbstractIntegrationTest {

    public static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:16-alpine").withExposedPorts(5432);

    @DynamicPropertySource
    public static void containersProperties(DynamicPropertyRegistry registry) {
        container.start();
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }


}
