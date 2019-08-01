package com.vironit.mwallet.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vironit.mwallet.model.attribute.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = RechargeTransactionRestDto.class, name = "RechargeTransaction"),
        @JsonSubTypes.Type(value = PaymentTransactionRestDto.class, name = "PaymentTransaction"),
        @JsonSubTypes.Type(value = MoneyTransferTransactionRestDto.class, name = "MoneyTransferTransaction")})
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class TransactionRestDto {

    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @NotNull(message = "Can't be null")
    private TransactionStatus status = TransactionStatus.CREATED;

    @NotNull(message = "Can't be null")
    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull(message = "Can't be null")
    @PastOrPresent
    private LocalDateTime updatedAt = LocalDateTime.now();

}
