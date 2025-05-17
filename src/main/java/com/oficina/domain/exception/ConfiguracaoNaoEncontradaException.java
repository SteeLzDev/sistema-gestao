package com.oficina.domain.exception;

public class ConfiguracaoNaoEncontradaException extends RuntimeException {
    public ConfiguracaoNaoEncontradaException(String message) {
        super(message);
    }

    public ConfiguracaoNaoEncontradaException (String tipoConfiguracao, Long id) {
        super(String.format("Configuração de %s com identificador %d não encontrada.", tipoConfiguracao, id));
    }
}
