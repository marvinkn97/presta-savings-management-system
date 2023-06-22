package dev.marvin.savingsmanagement.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(
                get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
                );
    }

    @Test
    void getCustomerById(){
    }

    @Test
    void registerCustomer(){
    }


    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void createAccount() {
    }

    @Test
    void getCustomerTotalSavings() {
    }
}