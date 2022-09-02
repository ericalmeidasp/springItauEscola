package com.itau.escolaItauSpring.service;

import com.itau.escolaItauSpring.config.mapper.AlunoMapperImpl;
import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
public class AlunoServiceIntegrationTest {

    @Autowired
    static AlunoService alunoService;
    @Autowired
    static AlunoRepository alunoRepository;

    static Aluno aluno;
    static Aluno aluno2;
    static AlunoRequest alunoRequest;

    @BeforeAll
    static void criarAlunos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        aluno.setCpf(30888L);
        aluno.setAtivado(Boolean.FALSE);
        alunoRepository.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Davi");
        aluno2.setCpf(30889L);
        aluno.setAtivado(Boolean.FALSE);
        alunoRepository.save(aluno2);

        AlunoRequest alunoRequest = new AlunoRequest();
        alunoRequest.setNome("Cristiano");
        alunoRequest.setCpf(308851L);
        alunoRequest.setIdade(12);
    }

    @Test
    void testAdicionar() {
        AlunoResponse alunoResponse = alunoService.adicionar(alunoRequest);

        Assertions.assertEquals(alunoResponse.getNome(), alunoRequest.getNome());

        Assertions.assertNotNull(alunoResponse.getId());
    }

    @Test
    void testAtivar() {
        aluno.setAtivado(Boolean.FALSE);

        alunoRepository.save(aluno);

        Assertions.assertFalse(aluno.getAtivado());
        alunoService.ativar(aluno.getId());

        Aluno alunoAtivado = alunoRepository.findById(aluno.getId()).orElseThrow();
        Assertions.assertTrue(alunoAtivado.getAtivado());
    }


    @Test
    void testDesativar() {
        aluno.setAtivado(Boolean.TRUE);

        alunoRepository.save(aluno);

        Assertions.assertTrue(aluno.getAtivado());

        alunoService.desativar(aluno.getId());

        Aluno alunoDesativado = alunoRepository.findById(aluno.getId()).orElseThrow();

        Assertions.assertFalse(alunoDesativado.getAtivado());
    }

    @Test
    void testListar() {
        List<AlunoResponse> alunoResponseList = alunoService.listar();

        Assertions.assertEquals(2, alunoResponseList.size());

        Assertions.assertEquals(aluno.getNome(), alunoResponseList.get(0).getNome());
    }

    @Test
    void testeLocalizar() {
        AlunoResponse alunoLocalizado = alunoService.localizar(aluno.getId());
        Assertions.assertEquals(aluno.getCpf(), alunoLocalizado.getCpf());
    }

    @Test
    void testQuantidadeAlunosAtivo() {
        aluno.setAtivado(Boolean.TRUE);
        alunoRepository.save(aluno);

        aluno2.setAtivado(Boolean.TRUE);
        alunoRepository.save(aluno2);

        Assertions.assertEquals(2, alunoService.quantidadeAlunosAtivo());
    }

    @Test
    void testRemoverPorCpf() {
        Assertions.assertTrue(alunoRepository.existsById(aluno.getId()));

        alunoService.removerPorCpf(aluno.getCpf());

        Assertions.assertFalse(alunoRepository.existsById(aluno.getId()));
    }

    @Test
    void testBuscarPorNome() {

        List<Aluno> alunoList = alunoService.buscarPorNome(aluno.getNome());

        Assertions.assertEquals(1, alunoList.size());

        Assertions.assertEquals(aluno.getNome(), alunoList.get(0).getNome());

    }

}
