package com.itau.escolaItauSpring.model;


import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Aluno {
    @Id
    @GeneratedValue
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @Column(name = "NOME", nullable = false, length = 200, unique = false)
    private String nome;
    @Column(name = "IDADE")
    private Integer idade;
    @Column(name = "CPF", nullable = false, unique = true)
    private Long cpf;
    @Column(name = "IS_ATIVO")
    private Boolean ativado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ENDERECO_ID")
    private Endereco endereco;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ALUNO_ID")
    private List<Nota> notas;

    //    public  Integer[] notas;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ALUNO_ID")
    private List<Curso> cursos;
}

