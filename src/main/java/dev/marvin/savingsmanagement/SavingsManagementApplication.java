package dev.marvin.savingsmanagement;

import dev.marvin.savingsmanagement.customer.Customer;
import dev.marvin.savingsmanagement.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class SavingsManagementApplication {

    private final CustomerRepository customerRepository;

    public static void main(String[] args) {

        SpringApplication.run(SavingsManagementApplication.class, args);

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args -> {
            Customer customer = Customer.builder()
                    .firstName("Marvin")
                    .lastName("Test")
                    .email("test@gmail.com")
                    .phoneNumber("0796856241")
                    .nationalID("33589754")
                    .address("Nairobi")
                    .build();

            customerRepository.save(customer);


        });
    }
}
