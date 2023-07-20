package dev.marvin.savingsmanagement.customer;

import jakarta.validation.constraints.NotBlank;

public record CustomerDto(

        @NotBlank(message = "Name is required") String name,

        @NotBlank(message = "Email is required") String email,

        @NotBlank(message = "National ID is required") String nationalID,

        @NotBlank(message = "Phone Number is required") String phoneNumber,

        @NotBlank(message = "Address is required") String address) {
}
