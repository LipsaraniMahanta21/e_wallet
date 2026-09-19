package com.e_wallet.Transaction.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    @NotEmpty
    private int from;
    @NotEmpty
    private int to;
    @NotNull
    private float amount;
}
