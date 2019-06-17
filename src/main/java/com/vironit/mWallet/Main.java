package com.vironit.mWallet;

import com.vironit.mWallet.models.*;
import com.vironit.mWallet.models.Currency;
import com.vironit.mWallet.services.RoleService;
import com.vironit.mWallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@SuppressWarnings("all")
@Component
public class Main {

    private static volatile Properties props = new Properties();
    private static String pathToPropertyFile = "src/main/resources/db.properties";
    private static File file = new File(pathToPropertyFile);

    public static void main(String[] args) throws InterruptedException {

    }



}



