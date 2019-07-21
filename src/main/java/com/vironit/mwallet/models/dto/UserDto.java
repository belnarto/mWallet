package com.vironit.mwallet.models.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private int id;
    private String name;
    private String login;

    @ToString.Exclude
    private String password;

    private RoleDto role;

    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<WalletDto> wallets;

}
