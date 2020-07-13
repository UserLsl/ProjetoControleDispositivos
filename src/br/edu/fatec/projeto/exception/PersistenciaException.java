package br.edu.fatec.projeto.exception;

public class PersistenciaException extends Exception {

    public PersistenciaException(String s) {
        super(s);
    }

    public PersistenciaException(String s, Exception e) {
        super(s, e);
    }
}
