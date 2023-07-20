package dev.marvin.savingsmanagement.savingsaccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountDto(

        @NotBlank @NotNull String accountType) {
}
