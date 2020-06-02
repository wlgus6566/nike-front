package com.nike.dnp.exception;

public class ManagerNotFoundException extends RuntimeException {

    public ManagerNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ManagerNotFoundException(String msg) {
        super(msg);
    }

    public ManagerNotFoundException() {
        super();
    }

}
