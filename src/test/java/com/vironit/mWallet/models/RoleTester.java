package com.vironit.mWallet.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTester {

    @Test
    void constructorTest() {
        try {
            new Role();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Role(RoleEnum.DEFAULT);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getIdTest() {
        //TODO
    }

    @Test
    void setAndGetNameTest() {
        //TODO
    }

    @Test
    void equalsTest() {
        //TODO
    }

}
