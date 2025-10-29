package br.com.feluz.jpa.exception;

public class TipoElementoNaoConhecidoException extends Exception {

    private static final long serialVersionUID = -2268140970978666251L;

    public TipoElementoNaoConhecidoException(String message) {
        super(message, null);
    }

    public TipoElementoNaoConhecidoException(String message, Throwable e) {
        super(message, e);
    }
}
