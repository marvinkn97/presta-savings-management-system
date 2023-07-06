package dev.marvin.savingsmanagement.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerDto(
        @NotNull(message = "First Name is required")
        String firstName,

        @NotNull(message = "Last Name is required")
        String lastName,

        @NotNull(message = "Email is required")
        @Email(regexp = "/&#40;[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+&#41;/g")
        String email,

        @NotNull(message = "Phone Number is required")
        String phoneNumber,

        @NotNull(message = "National ID Number is required")
        String nationalID,

        @NotNull(message = "Address is required")
        String address
) {}
