package dev.marvin.savingsmanagement.customer;

public record CustomerDto(
        String firstName, String lastName, String email, String phoneNumber, String nationalID,
        String address) {
}
