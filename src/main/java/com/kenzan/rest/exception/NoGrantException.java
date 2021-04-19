package com.kenzan.rest.exception;

public class NoGrantException extends RuntimeException  {

    private static final long serialVersionUID = -3_244_636_805_134_864_243L;

    public NoGrantException(String message) {
        super(message);
    }
}
