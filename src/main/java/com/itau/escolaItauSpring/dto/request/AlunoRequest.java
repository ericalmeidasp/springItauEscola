package com.itau.escolaItauSpring.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AlunoRequest {

    private String nome;
    private Integer idade;
    private Long cpf;
    private List<CursoRequest> cursos;
    private EnderecoRequest endereco;
    private List<NotaRequest> notas;
}
