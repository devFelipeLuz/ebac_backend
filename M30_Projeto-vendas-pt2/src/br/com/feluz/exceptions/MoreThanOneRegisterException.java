package br.com.feluz.exceptions;

public class MoreThanOneRegisterException extends Exception {

    private static final long serialVersionUID = -7509649433607067138L;

    public MoreThanOneRegisterException(String message) {
        super(message);
    }
}
