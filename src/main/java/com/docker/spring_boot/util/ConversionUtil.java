package com.docker.spring_boot.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ConversionUtil {

    public static String encode(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean matches(String password,String passwordDb){
        return new BCryptPasswordEncoder().matches(password,passwordDb);
    }

}
