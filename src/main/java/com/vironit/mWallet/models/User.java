package com.vironit.mWallet.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Transient
    private String passwordConfirm;
    // TODO fix

    public User() {
    }

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.login = builder.login;
        this.password = builder.password;
        this.role = builder.role;
        this.updatedAt = builder.updatedAt;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        if (newId > 0) {
            id = newId;
            updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if (newName != null && !newName.equals("")) {
            name = newName;
            updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("String is null or empty");
        }
    }

    public String getLogin() {
        return login;
    }

    @SuppressWarnings("unused")
    public void setLogin(String login) {
        if (login != null && !login.equals("")) {
            this.login = login;
            updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("String is null or empty");
        }
    }

    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        if (password != null && !password.equals("")) {
            this.password = password;
            updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("String is null or empty");
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role newRole) {
        if (newRole != null) {
            role = newRole;
            updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Role is null");
        }
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class UserBuilder {
        private int id;
        private String name;
        private String login;
        private String password;
        private Role role;
        private LocalDateTime updatedAt = LocalDateTime.now();

        public UserBuilder() {
        }

        public UserBuilder setId(int newId) {
            if (newId > 0) {
                this.id = newId;
                return this;
            } else {
                throw new IllegalArgumentException("Value <= 0");
            }
        }

        public UserBuilder setName(String newName) {
            if (newName != null && !newName.equals("")) {
                this.name = newName;
                return this;
            } else {
                throw new IllegalArgumentException("String is null or empty");
            }
        }

        public UserBuilder setLogin(String login) {
            if (login != null && !login.equals("")) {
                this.login = login;
                return this;
            } else {
                throw new IllegalArgumentException("String is null or empty");
            }
        }

        public UserBuilder setPassword(String password) {
            if (password != null && !password.equals("")) {
                this.password = password;
                return this;
            } else {
                throw new IllegalArgumentException("String is null or empty");
            }
        }

        public UserBuilder setRole(Role newRole) {
            if (newRole != null) {
                this.role = newRole;
                return this;
            } else {
                throw new IllegalArgumentException("Role is null");
            }
        }

        private boolean isDataValidForBuild() {
            return this.name != null && !this.name.equals("") &&
                    this.login != null && !this.login.equals("") &&
                    this.password != null && !this.password.equals("") &&
                    this.role != null;
        }

        public User build() {
            if (isDataValidForBuild()) {
                updatedAt = LocalDateTime.now();
                return new User(this);
            } else {
                throw new IllegalArgumentException("Validation in UserBuilder was not passed");
            }

        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                name.equals(user.name) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, role);
    }
}
