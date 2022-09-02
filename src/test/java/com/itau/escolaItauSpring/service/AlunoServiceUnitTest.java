package com.itau.escolaItauSpring.service;

//@ExtendWith(MockitoExtension.class) na classe de teste.
// @InjectMocks -> Na classe onde vai ser testada.
// @Mock -> Nas dependencias que vão ser simuladas.


import com.itau.escolaItauSpring.config.mapper.AlunoMapper;
import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceUnitTest {
    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private AlunoMapper alunoMapper;

    @Test
    void testAdicionar() {
        Mockito.when(alunoMapper.toModel(ArgumentMatchers.any(AlunoRequest.class))).thenReturn(new Aluno());
        Mockito.when(alunoRepository.save(ArgumentMatchers.any(Aluno.class))).thenReturn(new Aluno());
        Mockito.when(alunoMapper.toResponse(ArgumentMatchers.any(Aluno.class))).thenReturn(new AlunoResponse());

        AlunoResponse alunoResponse = alunoService.adicionar(new AlunoRequest());

        Assertions.assertInstanceOf(AlunoResponse.class, alunoResponse);
    }

    @Test
    void testAtivar() {
        Aluno aluno = new Aluno();
        aluno.setId(UUID.randomUUID());
        aluno.setAtivado(Boolean.FALSE);
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(aluno));

        Assertions.assertFalse(aluno.getAtivado());
        alunoService.ativar(aluno.getId());
        Assertions.assertTrue(aluno.getAtivado());
    }

    @Test
    void testDesativar() {
        Aluno aluno = new Aluno();
        aluno.setId(UUID.randomUUID());
        aluno.setAtivado(Boolean.TRUE);
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(aluno));

        Assertions.assertTrue(aluno.getAtivado());
        alunoService.desativar(aluno.getId());
        Assertions.assertFalse(aluno.getAtivado());
    }

    @Test
    void testeListar() {
        Mockito.when(alunoRepository.findAll()).thenReturn(new ArrayList<Aluno>());
        Mockito.when(alunoMapper.mapAluno(ArgumentMatchers.any())).thenReturn(new ArrayList<AlunoResponse>());
        var list = alunoService.listar();
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void testeLocalizar() {
        Optional<Aluno> aluno = Optional.of(new Aluno());
        aluno.get().setNome("Daiane");
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(aluno);

        AlunoResponse alunoResponse = new AlunoResponse();
        alunoResponse.setNome(aluno.get().getNome());
        Mockito.when(alunoMapper.toResponse(ArgumentMatchers.any())).thenReturn(alunoResponse);

        AlunoResponse alunoResponse1 = alunoService.localizar(UUID.randomUUID());
        Assertions.assertEquals(alunoResponse1.getNome(), alunoResponse.getNome());
    }

    @Test
    void testQuantidadeAlunosAtivo() {
        Mockito.when(alunoRepository.countAlunoByAtivado(ArgumentMatchers.any())).thenReturn(2L);
        long quantidade = alunoService.quantidadeAlunosAtivo();

        Assertions.assertEquals(2L, quantidade);
    }

    @Test
    void testRemoverPorCpf(){
        Long cpf = 123L;
        alunoService.removerPorCpf(cpf);
        Mockito.verify(alunoRepository,Mockito.times(1)).deleteByCpf(ArgumentMatchers.any(Long.class));
    }

    @Test
    void testBuscarPorNome() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Pedro");

        List<Aluno> alunoList = new ArrayList<>();
        alunoList.add(aluno1);

        Mockito.when(alunoRepository.findByNomeContainingIgnoreCase(ArgumentMatchers.any(String.class))).thenReturn(alunoList);

        List<Aluno> alunosListByService = alunoService.buscarPorNome("Pedro");

        Assertions.assertEquals(alunoList, alunosListByService);
    }
}

