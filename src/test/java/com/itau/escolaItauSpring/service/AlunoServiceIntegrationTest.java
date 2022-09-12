package com.itau.escolaItauSpring.service;

import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.helper.AlunoHelper;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.repository.AlunoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AlunoServiceIntegrationTest {

    private AlunoService alunoService;
    private AlunoRepository alunoRepository;
    private Aluno aluno;
    private List<String> list;

    @Autowired
    public AlunoServiceIntegrationTest(AlunoService service, AlunoRepository repository) {
        alunoService = service;
        alunoRepository = repository;
        this.aluno = AlunoHelper.criarAluno();
    }

    @BeforeEach
    void init() {
        var alunoId = alunoRepository.save(aluno).getId();
        aluno.setId(alunoId);
        list = new ArrayList<>(Arrays.asList(
                "testAtivar",
                "testDesativar",
                "testListar",
                "testeLocalizar",
                "testQuantidadeAlunosAtivo",
                "testRemoverPorCpf",
                "testBuscarPorNome"
        ));
    }

    @AfterEach
    void teardown() {
        alunoRepository.deleteByCpf(aluno.getCpf());
        list.clear();
    }

    @Test
    void testAdicionar() {
        AlunoRequest alunoRequest = new AlunoRequest();
        alunoRequest.setNome(aluno.getNome());
        alunoRequest.setCpf(aluno.getCpf() + 1);
        alunoRequest.setIdade(aluno.getIdade());

        AlunoResponse alunoResponse = alunoService.adicionar(alunoRequest);

        Assertions.assertEquals(alunoResponse.getNome(), alunoRequest.getNome());
        Assertions.assertNotNull(alunoResponse.getId());

        alunoRepository.deleteByCpf(aluno.getCpf() + 1);
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
        Assertions.assertTrue(aluno.getAtivado());

        alunoService.desativar(aluno.getId());
        Aluno alunoDesativado = alunoRepository.findById(aluno.getId()).orElseThrow();

        Assertions.assertFalse(alunoDesativado.getAtivado());
    }

    @Test
    void testListar() {
        List<AlunoResponse> alunoResponseList = alunoService.listar();

        Assertions.assertEquals(1, alunoResponseList.size());
        Assertions.assertEquals(aluno.getNome(), alunoResponseList.get(0).getNome());
    }

    @Test
    void testeLocalizar() {
        AlunoResponse alunoLocalizado = alunoService.localizar(aluno.getId());

        Assertions.assertEquals(aluno.getCpf(), alunoLocalizado.getCpf());
    }

    @Test
    void testQuantidadeAlunosAtivo() {
        Assertions.assertEquals(1, alunoService.quantidadeAlunosAtivo());
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
