package com.itau.escolaItauSpring.exception;

public class AlunoJaMatriculadoException extends RuntimeException {
    public AlunoJaMatriculadoException() {
    }

    public AlunoJaMatriculadoException(String message) {
        super(message);
    }
}
