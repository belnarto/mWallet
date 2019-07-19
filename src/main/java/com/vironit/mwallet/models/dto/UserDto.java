package com.vironit.mwallet.models.dto;

import com.vironit.mwallet.models.entity.Role;
import com.vironit.mwallet.models.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private int id;
    private String name;
    private String login;
    private String password;
    private Role role;
    private LocalDateTime updatedAt;
    private Set<Wallet> wallets;

}
