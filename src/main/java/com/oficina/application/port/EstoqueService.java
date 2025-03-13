package com.oficina.application.port;

import com.oficina.domain.model.Produto;

import java.util.List;

public interface EstoqueService {


    List<Produto> listarTodosProdutos();
   Produto buscarProdutoPorId (Long id);
   Produto buscarProdutoPorCodigo(String codigo);
    List<Produto> buscarProdutosPorCategoria (String categoria);
    List<Produto> buscarProdutosComEstoqueBaixo(int minimoEstoque);
    Produto salvarProduto (Produto produto);
    void removerProduto(Long id);
    void adicionarEstoque (Long produtoId, int quantidade);
    void removerEstoque (Long produtoId, int quantidade);


}
