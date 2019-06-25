package com.vironit.mWallet;

import com.vironit.mWallet.dao.RoleDao;
import com.vironit.mWallet.dao.impl.RoleDaoImpl;
import com.vironit.mWallet.models.*;
import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("all")
//@Component
public class Main {

    private static volatile Properties props = new Properties();
    private static String pathToPropertyFile = "src/main/resources/db.properties";
    private static File file = new File(pathToPropertyFile);

    public static RoleDao roleDao = new RoleDaoImpl();

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();

        User user =
                User.builder()
                        .id(1)
                        .login("Test11")
                        .name("Test11")
                        .password("Test11")
                        .role(main.roleDao.findById(37))
                        .updatedAt(LocalDateTime.now())
                        .build();
        Set<ConstraintViolation<User>> validates = validator.validate(user);
        System.out.println(validates.size());
        validates.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
        System.out.println(user);

        User user2 = user.toBuilder().id(2).build();
        System.out.println(user2);

    }



}



