package com.oficina.domain.exception;

public class PermissaoNaoEncontradaException extends  EntidadeNaoEncontradaException{

    public PermissaoNaoEncontradaException (String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long id) {
        super("Permissão", id);
    }

/*    public PermissaoNaoEncontradaException(String nome) {
        super("Permissão", nome);
    }*/
}
