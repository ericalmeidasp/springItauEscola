package com.itau.escolaItauSpring.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Curso {
    private Long id;
    private String nome;
    // Turno -> ENUM
    // Professor
    // Carga Horária
    // Data término
    // Ementa -> OUTRO OBJETO
}
