package com.vironit.mWallet;

import com.vironit.mWallet.dao.RoleDao;
import com.vironit.mWallet.dao.impl.RoleDaoImpl;
import com.vironit.mWallet.models.*;
import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
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
@Log4j2
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
        log.error("test1");
        log.debug("test2");
        log.fatal("test3");
    }

}

