package com.itau.escolaItauSpring.config.mapper;

import com.itau.escolaItauSpring.dto.request.*;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.dto.response.CursoResponse;
import com.itau.escolaItauSpring.model.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AlunoMapper {

    AlunoResponse toResponse(Aluno aluno);

    Aluno toModel(AlunoRequest alunoRequest);

    List<AlunoResponse> mapAluno(List<Aluno> alunos);

    @InheritInverseConfiguration
    CursoResponse cursoModelToResponse(Curso curso);

    @InheritInverseConfiguration
    Curso cursoRequestToModel(CursoRequest cursoRequest);

    @InheritInverseConfiguration
    Endereco enderecoRequestToModel(EnderecoRequest enderecoRequest);

    @InheritInverseConfiguration
    Nota notaRequestToModel(NotaRequest notaRequest);

    @InheritInverseConfiguration
    Professor professorRequestToModel(ProfessorRequest professorRequest);
}
