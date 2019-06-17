package com.vironit.mWallet.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoleTester {

    @Test
    public void constructorTest() {
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
    public void getIdTest() {
        //TODO
    }

    @Test
    public void setAndGetNameTest() {
        //TODO
    }

    @Test
    public void equalsTest() {
        //TODO
    }

}
