package com.oficina.infrastructure.config;


import com.oficina.application.port.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PermissaoInitializer implements CommandLineRunner {



    @Autowired
    private PermissaoService permissaoService;

    @Override
    public void run(String... args) throws Exception{
        // Inicializar permissões padrão
        permissaoService.inicializarPermissoes();
    }


}
