package com.oficina.application.service;


import com.oficina.application.port.PermissaoService;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.domain.model.Permissao;
import com.oficina.domain.model.Usuario;
import com.oficina.domain.model.UsuarioPermissao;
import com.oficina.infrastructure.persistence.repository.PermissaoRepository;
import com.oficina.infrastructure.persistence.repository.UsuarioPermissaoRepository;
import com.oficina.infrastructure.persistence.repository.UsuarioRepository;
import com.oficina.infrastructure.rest.dto.AtribuirPerfilDTO;
import com.oficina.infrastructure.rest.dto.AtualizarPermissoesDTO;
import com.oficina.infrastructure.rest.dto.PermissaoDTO;
import com.oficina.infrastructure.rest.dto.UsuarioPermissoesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissaoServiceImpl implements PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioPermissaoRepository usuarioPermissaoRepository;

    @Override
    @Transactional
    public void inicializarPermissoes(){
        //Lista de Permissões padrão.
        List<Permissao> permissoesPadrao = new ArrayList<>();

        //Permisões de Estoque
        adicionarPermissaoPadrao(permissoesPadrao, "ESTOQUE_VISUALIZAR", "Permissão para visualizar o estoque");
        adicionarPermissaoPadrao(permissoesPadrao, "ESTOQUE_ADICIONAR", "Permissão para adicionar itens ao estoque");
        adicionarPermissaoPadrao(permissoesPadrao, "ESTOQUE_EDITAR", "Permissão para editar itens do estoque");
        adicionarPermissaoPadrao(permissoesPadrao, "ESTOQUE_REMOVER", "Permissão para remover itens do estoque");

        //Permissões de Vendas
        adicionarPermissaoPadrao(permissoesPadrao, "VENDAS_VISUALIZAR", "Permissão para visualizar vendas");
        adicionarPermissaoPadrao(permissoesPadrao, "VENDAS_CRIAR", "Permissão para criar vendas");
        adicionarPermissaoPadrao(permissoesPadrao, "VENDAS_CANCELAR","Permissão para cancelar vendas");

        //Permissões de Fila
        adicionarPermissaoPadrao(permissoesPadrao, "FILA_VISUALIZAR", "Permissão para visualizar fila de clientes");
        adicionarPermissaoPadrao(permissoesPadrao, "FILA_GERENCIAR", "Permissão para gerenciar a fila de clientes");

        //Permissões de Relatórios
        adicionarPermissaoPadrao(permissoesPadrao, "RELATORIOS_VISUALIZAR", "Permissão para visualizar Relatórios");
        adicionarPermissaoPadrao(permissoesPadrao, "RELATORIOS_EXPORTAR", "Permissão para exportar relatórios ");

        //Permissões de Usuários
        adicionarPermissaoPadrao(permissoesPadrao, "USUARIOS_VISUALIZAR", "Permissão para visualizar usuários");
        adicionarPermissaoPadrao(permissoesPadrao, "USUARIOS_CRIAR", "Permissão para criar usuários");
        adicionarPermissaoPadrao(permissoesPadrao, "USUARIOS_EDITAR", "Permissão para editar usuários");
        adicionarPermissaoPadrao(permissoesPadrao, "USUARIOS_REMOVER", "Permissão para remover usuários");
        adicionarPermissaoPadrao(permissoesPadrao, "USUARIOS_PERMISSOES", "Permissão para gerenciar permissões de usuários");

        //Permissão de Configurações
        adicionarPermissaoPadrao(permissoesPadrao, "CONFIGURACOES_VISUALIZAR", "Permissão para visualizar configurações");
        adicionarPermissaoPadrao(permissoesPadrao, "CONFIGURACOES_EDITAR", "Permissão para editar configurações");

        //Salvar todas as permissões que não existem
        permissaoRepository.saveAll(permissoesPadrao);
    }

    private void adicionarPermissaoPadrao(List<Permissao> lista, String nome, String descricao){
        if (!permissaoRepository.existsByNome(nome)){
            Permissao permissao = new Permissao();
            permissao.setNome(nome);
            permissao.setDescricao(descricao);
            lista.add(permissao);
        }
    }
        //Lista todas as permissões disponíveis no sistema
    @Override
    public List<PermissaoDTO> listarTodasPermissoes(){
        List<Permissao> permissoes = permissaoRepository.findAll();
        return permissoes.stream()
                .map(p -> new PermissaoDTO(p.getId(), p.getNome(), p.getDescricao(), false ))
                .collect(Collectors.toList());
    }

    //Obtém as permissões de um usuário específico
    @Override
    public UsuarioPermissoesDTO obterPermissoesDoUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", usuarioId));

        List<Permissao> todasPermissoes = permissaoRepository.findAll();

        // Obter permissões do usuário
        List<UsuarioPermissao> permissoesDoUsuario = usuarioPermissaoRepository.findByUsuarioId(usuarioId);

        List<PermissaoDTO> permissoesDTO = todasPermissoes.stream()
                .map(p -> {
                    boolean selecionada = permissoesDoUsuario.stream()
                            .anyMatch(up -> up.getPermissao().getId() == p.getId());

                    return new  PermissaoDTO(p.getId(), p.getNome(), p.getDescricao(), selecionada);
                })
                .collect(Collectors.toList());

        return new UsuarioPermissoesDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getPerfil(),
                permissoesDTO
        );
    }

    @Override
    @Transactional
    public void atualizarPermissoesDoUsuario(AtualizarPermissoesDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario", dto.getUsuarioId()));

        usuarioPermissaoRepository.deleteByUsuarioId(usuario.getId());

        if (dto.getPermissaoIds() != null && !dto.getPermissaoIds().isEmpty()) {
            List<UsuarioPermissao> novasPermissoes = new ArrayList<>();

            for (Long permissaoId : dto.getPermissaoIds()) {
                Permissao permissao = permissaoRepository.findById(permissaoId)
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Permissão", permissaoId));

                UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
                usuarioPermissao.setUsuario(usuario);
                usuarioPermissao.setPermissao(permissao);
                novasPermissoes.add(usuarioPermissao);
            }
            usuarioPermissaoRepository.saveAll(novasPermissoes);
        }
    }

    @Override
    public boolean verificarPermissao(Long usuarioId, String nomePermissao) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (!usuarioOpt.isPresent()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();
        if ("ADMIN".equals(usuario.getPerfil())) {
            return true;
        }
        Optional<Permissao> permissaoOpt = permissaoRepository.findByNome(nomePermissao);
        if (!usuarioOpt.isPresent()) {
            return false;
        }

        return usuarioPermissaoRepository.existsByUsuarioAndPermissao(usuario, permissaoOpt.get());
    }

    @Override
    public List<String> obterNomesPermissoesDoUsuario(Long usuarioId){
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (!usuarioOpt.isPresent()) {
            return new ArrayList<>();
        }

        Usuario usuario = usuarioOpt.get();

        if ("ADMIN".equals(usuario.getPerfil())) {
            return permissaoRepository.findAll().stream()
                    .map(Permissao::getNome)
                    .collect(Collectors.toList());
        }

        return usuarioPermissaoRepository.findByUsuarioId(usuarioId).stream()
                .map(up -> up.getPermissao().getNome())
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void atribuirPermissoesPorPerfil (AtribuirPerfilDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário", dto.getUsuarioId()));

        //Atualizar Perfil do Usuário
        usuario.setPerfil(dto.getPerfil());
        usuarioRepository.save(usuario);

        //Remover permissões existentes
        usuarioPermissaoRepository.deleteByUsuarioId(usuario.getId());

        //Lista para armazenar as novas permissões
        List<UsuarioPermissao> novasPermissoes = new ArrayList<>();

        //Definir permissões com base no perfil
        List<String> permissoesNomes;

        switch (dto.getPerfil().toUpperCase()) {
            case "ADMINISTRADOR":
            case "ADMIN":

                //Administrador tem todas as permissões
                permissoesNomes = permissaoRepository.findAll().stream()
                        .map(Permissao::getNome)
                        .collect(Collectors.toList());
                break;

            case "GERENTE":
                    //Permissoes para Gerente
                permissoesNomes = Arrays.asList(
                        "USUARIOS_VISUALIZAR", "USUARIOS_CRIAR", "USUARIOS_EDITAR",
                        "ESTOQUE_VISUALIZAR", "ESTOQUE_ADICIONAR", "ESTOQUE_EDITAR", "ESTOQUE_REMOVER",
                        "VENDAS_VISUALIZAR", "VENDAS_CRIAR", "VENDAS_CANCELAR",
                        "FILA_VISUALIZAR", "FILA_GERENCIAR",
                        "RELATORIOS_VISUALIZAR", "RELATORIOS_EXPORTAR"
                );
                break;

            case "VENDEDOR":
                // Permissões de vendedor
                permissoesNomes = Arrays.asList(
                        "ESTOQUE_VISUALIZAR",
                        "VENDAS_VISUALIZAR", "VENDAS_CRIAR",
                        "FILA_VISUALIZAR",
                        "RELATORIOS_VISUALIZAR"
                );
                break;

            case "OPERADOR":
                // Permissões de operador
                permissoesNomes = Arrays.asList(
                        "ESTOQUE_VISUALIZAR",
                        "FILA_VISUALIZAR", "FILA_GERENCIAR"
                );
                break;

            default:
                // Permissões básicas para outros perfis
                permissoesNomes = Arrays.asList(
                        "ESTOQUE_VISUALIZAR",
                        "FILA_VISUALIZAR"
                );
                break;
        }

        //Buscar as permissões pelo nome e criar as associações
        for (String nome : permissoesNomes) {
            permissaoRepository.findByNome(nome).ifPresent(permissao -> {
                UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
                usuarioPermissao.setUsuario(usuario);
                usuarioPermissao.setPermissao(permissao);
                novasPermissoes.add(usuarioPermissao);
            });
        }

        //Salvar as Novas Permissões
        if (!novasPermissoes.isEmpty()) {
            usuarioPermissaoRepository.saveAll(novasPermissoes);
        }

    }

}
