package com.oficina.application.service;

import com.oficina.application.port.EstoqueService;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.domain.model.Produto;
import com.oficina.infrastructure.persistence.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class EstoqueServiceImpl implements EstoqueService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public EstoqueServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException
                        ("Produto", id));
    }

    @Override
    public Produto buscarProdutoPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException
                        ("Produto", codigo));
    }

    @Override
    public List<Produto> buscarProdutosPorCategoria (String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Produto> buscarProdutosComEstoqueBaixo(int minimoEstoque) {
        return produtoRepository.findByQuantidadeLessThan(minimoEstoque);
    }

    @Override
    @Transactional
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public void removerProduto (Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }


    @Override
    @Transactional
    public void adicionarEstoque (Long produtoId, int quantidade) {
        Produto produto = buscarProdutoPorId(produtoId);
        produto.adicionarEstoque(quantidade);
        produtoRepository.save(produto);

    }

    @Override
    @Transactional
    public void removerEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarProdutoPorId(produtoId);
        produto.removerEstoque(quantidade);
        produtoRepository.save(produto);
    }


}
