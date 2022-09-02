package com.itau.escolaItauSpring.service;

import com.itau.escolaItauSpring.config.mapper.AlunoMapperImpl;
import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.repository.AlunoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
public class AlunoServiceIntegrationTest {

    AlunoService alunoService;
    AlunoRepository alunoRepository;


    @Autowired
    public AlunoServiceIntegrationTest(AlunoService service, AlunoRepository repository) {
        alunoService = service;
        alunoRepository = repository;
    }

    @BeforeEach
    void criarAlunos() {
    }

    @AfterEach
    void removerAlunos() {

    }

    @Test
    void testAdicionar() {
        AlunoRequest alunoRequest = new AlunoRequest();
        alunoRequest.setNome("Cristiano");
        alunoRequest.setCpf(308851L);
        alunoRequest.setIdade(12);

        AlunoResponse alunoResponse = alunoService.adicionar(alunoRequest);

        Assertions.assertEquals(alunoResponse.getNome(), alunoRequest.getNome());

        Assertions.assertNotNull(alunoResponse.getId());

        alunoRepository.deleteByCpf(alunoResponse.getCpf());
    }

    @Test
    void testAtivar() {
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        aluno.setCpf(30888L);
        aluno.setAtivado(Boolean.FALSE);
        alunoRepository.save(aluno);

        Assertions.assertFalse(aluno.getAtivado());
        alunoService.ativar(aluno.getId());

        Aluno alunoAtivado = alunoRepository.findById(aluno.getId()).orElseThrow();
        Assertions.assertTrue(alunoAtivado.getAtivado());

        alunoRepository.deleteByCpf(alunoAtivado.getCpf());
    }


    @Test
    void testDesativar() {
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        aluno.setCpf(30888L);
        aluno.setAtivado(Boolean.TRUE);
        alunoRepository.save(aluno);

        Assertions.assertTrue(aluno.getAtivado());

        alunoService.desativar(aluno.getId());

        Aluno alunoDesativado = alunoRepository.findById(aluno.getId()).orElseThrow();

        Assertions.assertFalse(alunoDesativado.getAtivado());

        alunoRepository.deleteByCpf(alunoDesativado.getCpf());
    }

    @Test
    void testListar() {
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


        List<AlunoResponse> alunoResponseList = alunoService.listar();

        Assertions.assertEquals(2, alunoResponseList.size());

        Assertions.assertEquals(aluno.getNome(), alunoResponseList.get(0).getNome());

        alunoRepository.deleteByCpf(aluno.getCpf());
        alunoRepository.deleteByCpf(aluno2.getCpf());
    }

    @Test
    void testeLocalizar() {
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        aluno.setCpf(30888L);
        aluno.setAtivado(Boolean.FALSE);
        alunoRepository.save(aluno);

        AlunoResponse alunoLocalizado = alunoService.localizar(aluno.getId());
        Assertions.assertEquals(aluno.getCpf(), alunoLocalizado.getCpf());

        alunoRepository.deleteByCpf(alunoLocalizado.getCpf());
    }

    @Test
    void testQuantidadeAlunosAtivo() {
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        aluno.setCpf(30888L);
        aluno.setAtivado(Boolean.TRUE);
        alunoRepository.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Davi");
        aluno2.setCpf(30889L);
        aluno2.setAtivado(Boolean.TRUE);
        alunoRepository.save(aluno2);

        Assertions.assertEquals(2, alunoService.quantidadeAlunosAtivo());

        alunoRepository.deleteByCpf(aluno.getCpf());
        alunoRepository.deleteByCpf(aluno2.getCpf());
    }

    @Test
    void testRemoverPorCpf() {
        Aluno aluno2 = new Aluno();
        aluno2.setNome("Davi");
        aluno2.setCpf(30889L);
        aluno2.setAtivado(Boolean.TRUE);
        alunoRepository.save(aluno2);

        Assertions.assertTrue(alunoRepository.existsById(aluno2.getId()));

        alunoService.removerPorCpf(aluno2.getCpf());

        Assertions.assertFalse(alunoRepository.existsById(aluno2.getId()));
    }

    @Test
    void testBuscarPorNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        aluno.setCpf(30888L);
        aluno.setAtivado(Boolean.TRUE);
        alunoRepository.save(aluno);

        List<Aluno> alunoList = alunoService.buscarPorNome(aluno.getNome());

        Assertions.assertEquals(1, alunoList.size());

        Assertions.assertEquals(aluno.getNome(), alunoList.get(0).getNome());

        alunoRepository.deleteByCpf(aluno.getCpf());

    }

}
