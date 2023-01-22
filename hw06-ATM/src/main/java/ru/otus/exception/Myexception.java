package ru.otus.exception;

public class Myexception extends RuntimeException {
    public Myexception(Exception e) {
        super(e);
    }
    public Myexception(String msg) {
        super(msg);
    }
    public Myexception(String errorMsg, Exception e) {
        super(errorMsg, e);
    }
}
