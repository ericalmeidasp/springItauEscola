package com.itau.escolaItauSpring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
public class Curso {
    @Id
    @GeneratedValue
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    private Professor professor;
}
