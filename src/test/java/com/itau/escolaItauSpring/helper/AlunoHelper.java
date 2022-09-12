package com.itau.escolaItauSpring.helper;

import com.itau.escolaItauSpring.model.Aluno;

import java.util.UUID;

public class AlunoHelper {

    public static Aluno criarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(UUID.randomUUID());
        aluno.setNome("Daiane");
        aluno.setIdade(29);
        aluno.setCpf(12345678900l);
        aluno.setAtivado(Boolean.TRUE);
        return aluno;
    }
}
