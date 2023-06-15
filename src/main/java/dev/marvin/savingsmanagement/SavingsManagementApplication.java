package dev.marvin.savingsmanagement;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class SavingsManagementApplication {

    public static void main(String[] args) {

        SpringApplication.run(SavingsManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args -> {
        });
    }
}
