package com.vironit.mWallet;

import java.io.*;
import java.util.Properties;

public class Main {

    private static volatile Properties props = new Properties();
    private static String pathToPropertyFile = "src/main/resources/db.properties";
    private static File file = new File(pathToPropertyFile);

    public static void main(String[] args) {

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            props.load(bufferedInputStream);
            System.out.println(props);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}