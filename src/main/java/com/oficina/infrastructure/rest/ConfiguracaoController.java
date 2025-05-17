package com.oficina.infrastructure.rest;


import com.oficina.application.port.ConfiguracaoService;
import com.oficina.domain.model.ConfiguracaoBancoDados;
import com.oficina.domain.model.ConfiguracaoGeral;
import com.oficina.infrastructure.rest.dto.ConfiguracaoBancoDadosDTO;
import com.oficina.infrastructure.rest.dto.ConfiguracaoGeralDTO;
import com.oficina.infrastructure.rest.dto.ConfiguracaoNotificacaoDTO;
import com.oficina.infrastructure.rest.dto.ConfiguracaoSegurancaDTO;
import jakarta.servlet.http.PushBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/configuracoes")
public class ConfiguracaoController {


    private final ConfiguracaoService configuracaoService;

    @Autowired
    public ConfiguracaoController (ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }
    //Configuração Geral
    @GetMapping("/geral")
    public ResponseEntity<ConfiguracaoGeralDTO> obterConfiguracaoGeral() {
        return ResponseEntity.ok(configuracaoService.obterConfiguracaoGeral());
    }

    @PostMapping("/geral")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConfiguracaoGeralDTO> salvarConfiguracaoGeral(@Valid @RequestBody ConfiguracaoGeralDTO dto) {
        return ResponseEntity.ok(configuracaoService.salvarConfiguracaoGeral(dto));
    }
    //Configuracao Banco de Dados
    @GetMapping("/banco-dados")
    @PreAuthorize("asRole('ADMIN')")
    public ResponseEntity<ConfiguracaoBancoDadosDTO> obterConfiguracoesBancoDados(){
        return ResponseEntity.ok(configuracaoService.obterConfiguracaoBancoDados());
    }

    @PostMapping("/banco-dados")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConfiguracaoBancoDadosDTO> salvarConfiguracaoBancoDados(@Valid @RequestBody ConfiguracaoBancoDadosDTO dto) {
        return ResponseEntity.ok(configuracaoService.salvarConfiguracaoBancoDados(dto));
    }

    @PostMapping("banco-dados-testar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> testarConexaoBancoDados(@RequestBody ConfiguracaoBancoDadosDTO dto) {
        Boolean resultado = configuracaoService.testarConexaoBancoDados(dto);
        return ResponseEntity.ok(resultado);
    }

    //Configuracao Notificação

    @GetMapping("/notificacao")
    public ResponseEntity<ConfiguracaoNotificacaoDTO> obterConfiguracaoNotificacao() {
        return ResponseEntity.ok(configuracaoService.obterConfiguracaoNotificacao());
    }

    @PostMapping("/notificacao")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConfiguracaoNotificacaoDTO> salvarConfiguracaoNotificacao (@Valid @RequestBody ConfiguracaoNotificacaoDTO dto) {
        return ResponseEntity.ok(configuracaoService.salvarConfiguracaoNotificacao(dto));
    }

    //Configuracao Segurança

    @GetMapping("/seguranca")
    public ResponseEntity<ConfiguracaoSegurancaDTO> obterConfiguracaoSeguranca() {
        return ResponseEntity.ok(configuracaoService.obterConfiguracaoSeguranca());
    }

    @PostMapping("/seguranca")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConfiguracaoSegurancaDTO> salvarConfiguracaoSeguranca(@Valid @RequestBody ConfiguracaoSegurancaDTO dto) {
        return ResponseEntity.ok(configuracaoService.salvarConfiguracaoSeguranca(dto));
    }

}
