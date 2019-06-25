package com.vironit.mWallet.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "users")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "Can't be negative")
    @Builder.Default
    private int id = 0;

    @Column(name = "name")
    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 4 and less than 60")
    private String name;

    @Column(name = "login")
    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 4 and less than 60")
    private String login;

    @Column(name = "password")
    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 4 and less than 60")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull(message = "Can't be null")
    private Role role;

    @Column(name = "updatedAt")
    @NotNull(message = "Can't be null")
    @Past
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Singular
    @EqualsAndHashCode.Exclude
    private Set<Wallet> wallets;

}
