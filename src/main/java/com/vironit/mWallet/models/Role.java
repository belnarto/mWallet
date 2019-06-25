package com.vironit.mWallet.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    public Role() {
    }

    public Role(RoleEnum roleEnum) {
        setRoleEnum(roleEnum);
    }

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        if (newId > 0) {
            id = newId;
        } else {
            throw new IllegalArgumentException("Value <= 0");
        }
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum newRoleEnum) {
        if (newRoleEnum != null) {
            roleEnum = newRoleEnum;
        } else {
            throw new IllegalArgumentException("newRoleEnum is null");
        }

    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleEnum=" + roleEnum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role1 = (Role) o;
        return id == role1.id &&
                roleEnum == role1.roleEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleEnum);
    }
}
