package com.oficina.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtil {

    public static void main(String [] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode("admin123");
        System.out.println("Senha criptografada: " + senhaCriptografada);

        System.out.println("UPDATE usuarios SET senha = '" + senhaCriptografada + "'WHERE username =  'admin';");
    }

}
