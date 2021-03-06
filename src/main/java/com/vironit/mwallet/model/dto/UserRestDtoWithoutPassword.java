package com.vironit.mwallet.model.dto;

import com.vironit.mwallet.service.validator.RoleIdExists;
import com.vironit.mwallet.service.validator.UniqueLogin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@UniqueLogin
public class UserRestDtoWithoutPassword {

    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 3 and less than 61")
    private String name;

    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 3 and less than 61")
//    @LoginUnique
    private String login;

    @NotNull(message = "Can't be null")
    @RoleIdExists
    private String roleId;

    @NotNull(message = "Can't be null")
    @PastOrPresent
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Set<WalletDto> wallets;

}
