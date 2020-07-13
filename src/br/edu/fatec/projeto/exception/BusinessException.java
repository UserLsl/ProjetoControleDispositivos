package br.edu.fatec.projeto.exception;

public class BusinessException extends Exception {

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(String s, Exception e) {
        super(s, e);
    }
}
