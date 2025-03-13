package com.oficina.infrastructure.rest;

import com.oficina.application.port.EstoqueService;
import com.oficina.domain.exception.EntidadeNaoEncontradaException;
import com.oficina.domain.model.Produto;
import com.oficina.infrastructure.rest.dto.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private final EstoqueService estoqueService;


    @Autowired
    public ProdutoController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity <List<ProdutoDTO>> listarProdutos() {
        List<Produto> produtos = estoqueService.listarTodosProdutos();
        List<ProdutoDTO> produtosDTO = produtos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProduto(@PathVariable Long id) {
        try {
            Produto produto = estoqueService.buscarProdutoPorId(id);
            return ResponseEntity.ok(convertToDTO(produto));

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorCodigo(@PathVariable String codigo) {
      try {
          Produto produto = estoqueService.buscarProdutoPorCodigo(codigo);
          return ResponseEntity.ok(convertToDTO(produto));
      } catch (EntidadeNaoEncontradaException e ) {
          return ResponseEntity.notFound().build();
      }
    }
    @GetMapping("/categoria/{categogia}")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = estoqueService.buscarProdutosPorCategoria(categoria);
        List<ProdutoDTO> produtosDTO = produtos.stream()
                .map(this :: convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosDTO);
    }

    @PostMapping//("/{id}")
    public ResponseEntity<ProdutoDTO> criarProduto (@RequestBody ProdutoDTO produtoDTO) {
        Produto produto = converteToEntity(produtoDTO);
        Produto produtoSalvo = estoqueService.salvarProduto(produto);
        return new ResponseEntity<>(convertToDTO(produtoSalvo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto (@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            Produto produtoInexistente = estoqueService.buscarProdutoPorId(id);
            Produto produtoAtualizado = converteToEntity(produtoDTO);
            produtoAtualizado.setId(id);
            Produto produtoSalvo = estoqueService.salvarProduto(produtoAtualizado);
            return ResponseEntity.ok(convertToDTO(produtoSalvo));
        } catch (EntidadeNaoEncontradaException e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        try {
            estoqueService.removerProduto(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/adicionar-estoque")
    public ResponseEntity<Void> adicionarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        try {
            estoqueService.adicionarEstoque(id, quantidade);
            return ResponseEntity.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

        @PostMapping("/{id}/remover-estoque")
        public ResponseEntity<Void> removerEstoque(@PathVariable Long id, @RequestParam int quantidade) {
            try {
                estoqueService.removerEstoque(id, quantidade);
                return ResponseEntity.ok().build();
            } catch (EntidadeNaoEncontradaException e ) {
                return ResponseEntity.notFound().build();
            } catch (IllegalArgumentException | IllegalStateException e) {
                return ResponseEntity.badRequest().build();
            }
        }



    private ProdutoDTO convertToDTO (Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getCodigo(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getQuantidade(),
                produto.getPreco()
        );
    }

    private Produto converteToEntity (ProdutoDTO dto) {
        return new Produto(
                dto.getId(),
                dto.getCodigo(),
                dto.getNome(),
                dto.getCategoria(),
                dto.getQuantidade(),
                dto.getPreco()
        );
    }





}
