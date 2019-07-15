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
    @Builder.Default
    @PositiveOrZero(message = "Can't be negative")
    private int id = 0;

    @Column(name = "name")
    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 3 and less than 61")
    private String name;

    @Column(name = "login")
    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 3 and less than 61")
    private String login;

    @Column(name = "password")
    @NotNull(message = "Can't be null")
    @Size(min = 4, max = 60, message = "Should be bigger than 3 and less than 61")
    @ToString.Exclude
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull(message = "Can't be null")
    private Role role;

    @Column(name = "updatedAt")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    @NotNull(message = "Can't be null")
    @Past
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Singular
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Wallet> wallets;

}
