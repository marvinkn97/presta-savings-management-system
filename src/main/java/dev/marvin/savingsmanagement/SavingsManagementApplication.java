package dev.marvin.savingsmanagement;

import dev.marvin.savingsmanagement.customer.Customer;
import dev.marvin.savingsmanagement.customer.CustomerRepository;
import dev.marvin.savingsmanagement.savingsaccount.Account;
import dev.marvin.savingsmanagement.savingsaccount.AccountRepository;
import dev.marvin.savingsmanagement.savingsaccount.AccountType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
public class SavingsManagementApplication {

    public static void main(String... args) {
        SpringApplication.run(SavingsManagementApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository, AccountRepository accountRepository) {
        return args -> {
            Customer customer = new Customer();
            customer.setName("John Doe");
            customer.setEmail("jdoe@gmail.com");
            customer.setNationalId("33817652");
            customer.setPhoneNumber("0790860297");
            customer.setAddress("Nairobi");

            Account account = new Account();
            account.setAccountType(AccountType.VACATION);
            account.setAccountNumber(UUID.randomUUID().toString());
            account.setBalance(BigDecimal.ZERO);
            account.setCustomer(customer);

            customerRepository.save(customer);
            accountRepository.save(account);
        };
    }
}
