package com.oficina.application.service;

import com.oficina.application.port.ConfiguracaoService;
import com.oficina.domain.model.*;
import com.oficina.infrastructure.persistence.repository.*;
import com.oficina.infrastructure.rest.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConfiguracaoServiceImpl implements ConfiguracaoService {

    private final ConfiguracaoGeralRepository configuracaoGeralRepository;
    private final ConfiguracaoBancoDadosRepository configuracaoBancoDadosRepository;
    private final ConfiguracaoNotificacaoRepository configuracaoNotificacaoRepository;
    private final ConfiguracaoSegurancaRepository configuracaoSegurancaRepository;
    private final HistoricoConfiguracaoRepository historicoConfiguracaoRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ConfiguracaoServiceImpl(
            ConfiguracaoGeralRepository configuracaoGeralRepository,
            ConfiguracaoBancoDadosRepository configuracaoBancoDadosRepository,
            ConfiguracaoNotificacaoRepository configuracaoNotificacaoRepository,
            ConfiguracaoSegurancaRepository configuracaoSegurancaRepository,
            HistoricoConfiguracaoRepository historicoConfiguracaoRepository,
            ObjectMapper objectMapper) {
        this.configuracaoGeralRepository = configuracaoGeralRepository;
        this.configuracaoBancoDadosRepository = configuracaoBancoDadosRepository;
        this.configuracaoNotificacaoRepository = configuracaoNotificacaoRepository;
        this.configuracaoSegurancaRepository = configuracaoSegurancaRepository;
        this.historicoConfiguracaoRepository = historicoConfiguracaoRepository;
        this.objectMapper = objectMapper;
    }

    // Métodos para Configuração Geral

    @Override
    public ConfiguracaoGeralDTO obterConfiguracaoGeral() {
        Optional<ConfiguracaoGeral> configuracao = configuracaoGeralRepository.findAtiva();
        return configuracao.map(ConfiguracaoGeralDTO::new)
                .orElseGet(() -> {
                    ConfiguracaoGeral novaConfig = new ConfiguracaoGeral();
                    novaConfig.setNomeEmpresa("Oficina Mecânica");
                    novaConfig.setEmail("contato@oficina.com");
                    return new ConfiguracaoGeralDTO(novaConfig);
                });
    }

    @Override
    @Transactional
    public ConfiguracaoGeralDTO salvarConfiguracaoGeral(ConfiguracaoGeralDTO dto) {
        ConfiguracaoGeral entity = dto.toEntity();

        // Se for uma atualização, desativar a configuração anterior
        if (entity.getId() != null) {
            Optional<ConfiguracaoGeral> antigaOpt = configuracaoGeralRepository.findById(entity.getId());
            if (antigaOpt.isPresent()) {
                ConfiguracaoGeral antiga = antigaOpt.get();
                registrarHistorico("GERAL", antiga.getId(), objectMapper.valueToTree(antiga).toString());
            }
        } else {
            // Desativar todas as configurações ativas
            configuracaoGeralRepository.findAtiva().ifPresent(config -> {
                config.setAtivo(false);
                configuracaoGeralRepository.save(config);
                registrarHistorico("GERAL", config.getId(), objectMapper.valueToTree(config).toString());
            });
        }

        // Definir usuário que está fazendo a alteração
        entity.setUsuarioCriacao(getUsuarioLogado());

        // Salvar nova configuração
        entity = configuracaoGeralRepository.save(entity);

        return new ConfiguracaoGeralDTO(entity);
    }

    // Métodos para Configuração de Banco de Dados

    @Override
    public ConfiguracaoBancoDadosDTO obterConfiguracaoBancoDados() {
        Optional<ConfiguracaoBancoDados> configuracao = configuracaoBancoDadosRepository.findAtiva();
        return configuracao.map(ConfiguracaoBancoDadosDTO::new)
                .orElseGet(() -> {
                    ConfiguracaoBancoDados novaConfig = new ConfiguracaoBancoDados();
                    novaConfig.setHost("localhost");
                    novaConfig.setPorta("5432");
                    novaConfig.setNomeBanco("oficina_mecanica");
                    novaConfig.setUsuario("postgres");
                    novaConfig.setTipoBanco(ConfiguracaoBancoDados.TipoBancoDados.POSTGRES);
                    return new ConfiguracaoBancoDadosDTO(novaConfig);
                });
    }

    @Override
    @Transactional
    public ConfiguracaoBancoDadosDTO salvarConfiguracaoBancoDados(ConfiguracaoBancoDadosDTO dto) {
        ConfiguracaoBancoDados entity = dto.toEntity();

        // Se for uma atualização, desativar a configuração anterior
        if (entity.getId() != null) {
            Optional<ConfiguracaoBancoDados> antigaOpt = configuracaoBancoDadosRepository.findById(entity.getId());
            if (antigaOpt.isPresent()) {
                ConfiguracaoBancoDados antiga = antigaOpt.get();
                // Se a senha não foi fornecida, manter a senha atual
                if (entity.getSenha() == null || entity.getSenha().isEmpty()) {
                    entity.setSenha(antiga.getSenha());
                }
                registrarHistorico("BANCO_DADOS", antiga.getId(), objectMapper.valueToTree(antiga).toString());
            }
        } else {
            // Desativar todas as configurações ativas
            configuracaoBancoDadosRepository.findAtiva().ifPresent(config -> {
                config.setAtivo(false);
                configuracaoBancoDadosRepository.save(config);
                registrarHistorico("BANCO_DADOS", config.getId(), objectMapper.valueToTree(config).toString());
            });
        }

        // Definir usuário que está fazendo a alteração
        entity.setUsuarioCriacao(getUsuarioLogado());

        // Salvar nova configuração
        entity = configuracaoBancoDadosRepository.save(entity);

        return new ConfiguracaoBancoDadosDTO(entity);
    }

    @Override
    public Boolean testarConexaoBancoDados(ConfiguracaoBancoDadosDTO dto) {
        // Aqui seria implementada a lógica para testar a conexão com o banco de dados
        // Por simplicidade, estamos apenas simulando o teste
        try {
            Thread.sleep(1000); // Simular um teste que leva algum tempo
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Métodos para Configuração de Notificação

    @Override
    public ConfiguracaoNotificacaoDTO obterConfiguracaoNotificacao() {
        Optional<ConfiguracaoNotificacao> configuracao = configuracaoNotificacaoRepository.findAtiva();
        return configuracao.map(ConfiguracaoNotificacaoDTO::new)
                .orElseGet(() -> {
                    ConfiguracaoNotificacao novaConfig = new ConfiguracaoNotificacao();
                    return new ConfiguracaoNotificacaoDTO(novaConfig);
                });
    }

    @Override
    @Transactional
    public ConfiguracaoNotificacaoDTO salvarConfiguracaoNotificacao(ConfiguracaoNotificacaoDTO dto) {
        ConfiguracaoNotificacao entity = dto.toEntity();

        // Se for uma atualização, desativar a configuração anterior
        if (entity.getId() != null) {
            Optional<ConfiguracaoNotificacao> antigaOpt = configuracaoNotificacaoRepository.findById(entity.getId());
            if (antigaOpt.isPresent()) {
                ConfiguracaoNotificacao antiga = antigaOpt.get();
                registrarHistorico("NOTIFICACAO", antiga.getId(), objectMapper.valueToTree(antiga).toString());
            }
        } else {
            // Desativar todas as configurações ativas
            configuracaoNotificacaoRepository.findAtiva().ifPresent(config -> {
                config.setAtivo(false);
                configuracaoNotificacaoRepository.save(config);
                registrarHistorico("NOTIFICACAO", config.getId(), objectMapper.valueToTree(config).toString());
            });
        }

        // Definir usuário que está fazendo a alteração
        entity.setUsuarioCriacao(getUsuarioLogado());

        // Salvar nova configuração
        entity = configuracaoNotificacaoRepository.save(entity);

        return new ConfiguracaoNotificacaoDTO(entity);
    }

    // Métodos para Configuração de Segurança

    @Override
    public ConfiguracaoSegurancaDTO obterConfiguracaoSeguranca() {
        Optional<ConfiguracaoSeguranca> configuracao = configuracaoSegurancaRepository.findAtiva();
        return configuracao.map(ConfiguracaoSegurancaDTO::new)
                .orElseGet(() -> {
                    ConfiguracaoSeguranca novaConfig = new ConfiguracaoSeguranca();
                    return new ConfiguracaoSegurancaDTO(novaConfig);
                });
    }

    @Override
    @Transactional
    public ConfiguracaoSegurancaDTO salvarConfiguracaoSeguranca(ConfiguracaoSegurancaDTO dto) {
        ConfiguracaoSeguranca entity = dto.toEntity();

        // Se for uma atualização, desativar a configuração anterior
        if (entity.getId() != null) {
            Optional<ConfiguracaoSeguranca> antigaOpt = configuracaoSegurancaRepository.findById(entity.getId());
            if (antigaOpt.isPresent()) {
                ConfiguracaoSeguranca antiga = antigaOpt.get();
                registrarHistorico("SEGURANCA", antiga.getId(), objectMapper.valueToTree(antiga).toString());
            }
        } else {
            // Desativar todas as configurações ativas
            configuracaoSegurancaRepository.findAtiva().ifPresent(config -> {
                config.setAtivo(false);
                configuracaoSegurancaRepository.save(config);
                registrarHistorico("SEGURANCA", config.getId(), objectMapper.valueToTree(config).toString());
            });
        }

        // Definir usuário que está fazendo a alteração
        entity.setUsuarioCriacao(getUsuarioLogado());

        // Salvar nova configuração
        entity = configuracaoSegurancaRepository.save(entity);

        return new ConfiguracaoSegurancaDTO(entity);
    }

    // Método auxiliar para registrar histórico de alterações
    private void registrarHistorico(String tipoConfiguracao, Long idConfiguracao, String detalhes) {
        HistoricoConfiguracao historico = new HistoricoConfiguracao();
        historico.setTipoConfiguracao(tipoConfiguracao);
        historico.setIdConfiguracao(idConfiguracao);
        historico.setUsuario(getUsuarioLogado());
        historico.setDetalhes(detalhes);
        historicoConfiguracaoRepository.save(historico);
    }

    // Método auxiliar para obter o usuário logado
    private String getUsuarioLogado() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                return auth.getName();
            }
        } catch (Exception e) {
            // Ignorar erros
        }
        return "sistema";
    }
}
