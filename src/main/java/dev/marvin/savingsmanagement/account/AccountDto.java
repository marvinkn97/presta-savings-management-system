package dev.marvin.savingsmanagement.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountDto(

        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank()
        String accountType) {
}
