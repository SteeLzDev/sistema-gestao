package com.oficina.application.port;

import com.oficina.infrastructure.rest.dto.ConfiguracaoBancoDadosDTO;
import com.oficina.infrastructure.rest.dto.ConfiguracaoGeralDTO;
import com.oficina.infrastructure.rest.dto.ConfiguracaoNotificacaoDTO;
import com.oficina.infrastructure.rest.dto.ConfiguracaoSegurancaDTO;

public interface ConfiguracaoService {

    //Configurações Gerais
    ConfiguracaoGeralDTO obterConfiguracaoGeral();
    ConfiguracaoGeralDTO salvarConfiguracaoGeral (ConfiguracaoGeralDTO dto);

    //Configurações de BD
    ConfiguracaoBancoDadosDTO obterConfiguracaoBancoDados();
    ConfiguracaoBancoDadosDTO salvarConfiguracaoBancoDados(ConfiguracaoBancoDadosDTO dto);
    Boolean testarConexaoBancoDados(ConfiguracaoBancoDadosDTO dto);

    //Configurações Notificações
    ConfiguracaoNotificacaoDTO obterConfiguracaoNotificacao();
    ConfiguracaoNotificacaoDTO salvarConfiguracaoNotificacao(ConfiguracaoNotificacaoDTO dto);

    //Configurações de Segurança
    ConfiguracaoSegurancaDTO obterConfiguracaoSeguranca();
    ConfiguracaoSegurancaDTO salvarConfiguracaoSeguranca(ConfiguracaoSegurancaDTO dto);







}
