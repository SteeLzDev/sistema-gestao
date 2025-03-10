package com.oficina.domain.exception;

public class EntidadeNaoEncontradaException extends  RuntimeException{


    public EntidadeNaoEncontradaException (String message) {
        super(message);
    }

    public EntidadeNaoEncontradaException (String entidade, Long id) {
        super (String.format("%s com id dentificador %s não encontrado(a). ", entidade, id));
    }

    public EntidadeNaoEncontradaException (String entidade, String identificador) {
        super (String.format("%s com identificador %s não encontrado(a)", entidade, identificador));
    }


}
