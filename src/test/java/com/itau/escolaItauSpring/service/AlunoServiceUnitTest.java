package com.itau.escolaItauSpring.service;

import com.itau.escolaItauSpring.config.mapper.AlunoMapper;
import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.exception.ItemNaoExistenteException;
import com.itau.escolaItauSpring.helper.AlunoHelper;
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

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceUnitTest {
    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private AlunoMapper alunoMapper;

    private Aluno aluno;

    public AlunoServiceUnitTest() {
        this.aluno = AlunoHelper.criarAluno();
    }

    @Test
    void testAdicionar() {
        Mockito.when(alunoMapper.toModel(ArgumentMatchers.any(AlunoRequest.class))).thenReturn(aluno);
        Mockito.when(alunoRepository.save(ArgumentMatchers.any(Aluno.class))).thenReturn(aluno);
        Mockito.when(alunoMapper.toResponse(ArgumentMatchers.any(Aluno.class))).thenReturn(new AlunoResponse());

        alunoService.adicionar(new AlunoRequest());

        Mockito.verify(alunoMapper, Mockito.times(1)).toModel(Mockito.any());
        Mockito.verify(alunoRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(alunoMapper, Mockito.times(1)).toResponse(Mockito.any());
    }

    @Test
    void testAtivar() {
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(aluno));
        aluno.setAtivado(Boolean.FALSE);

        Assertions.assertFalse(aluno.getAtivado());
        alunoService.ativar(aluno.getId());

        Assertions.assertTrue(aluno.getAtivado());
        Mockito.verify(alunoRepository, Mockito.times(1)).save(aluno);
    }

    @Test
    void testAtivarException() {
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNaoExistenteException.class, () -> alunoService.ativar(UUID.randomUUID()));
        Mockito.verify(alunoRepository, Mockito.times(0)).save(aluno);
    }

    @Test
    void testDesativar() {
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(aluno));
        aluno.setAtivado(Boolean.TRUE);

        Assertions.assertTrue(aluno.getAtivado());
        alunoService.desativar(aluno.getId());

        Assertions.assertFalse(aluno.getAtivado());
        Mockito.verify(alunoRepository, Mockito.times(1)).save(aluno);
    }

    @Test
    void testDesativarException() {
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNaoExistenteException.class, () -> alunoService.desativar(UUID.randomUUID()));
        Mockito.verify(alunoRepository, Mockito.times(0)).save(aluno);
    }

    @Test
    void testListar() {
        Mockito.when(alunoRepository.findAll()).thenReturn(new ArrayList<Aluno>());
        Mockito.when(alunoMapper.mapAluno(ArgumentMatchers.any())).thenReturn(new ArrayList<AlunoResponse>());

        List<AlunoResponse> list = alunoService.listar();

        Assertions.assertEquals(0, list.size());
    }

    @Test
    void testLocalizar() {
        AlunoResponse alunoResponse = new AlunoResponse();
        alunoResponse.setId(aluno.getId());
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(aluno));
        Mockito.when(alunoMapper.toResponse(ArgumentMatchers.any())).thenReturn(alunoResponse);

        AlunoResponse resultado = alunoService.localizar(aluno.getId());

        Assertions.assertEquals(resultado.getId(), alunoResponse.getId());
    }

    @Test
    void testLocalizarException() {
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNaoExistenteException.class, () -> alunoService.localizar(UUID.randomUUID()));
    }

    @Test
    void testQuantidadeAlunosAtivo() {
        Mockito.when(alunoRepository.countAlunoByAtivado(ArgumentMatchers.any())).thenReturn(2L);

        long quantidade = alunoService.quantidadeAlunosAtivo();

        Assertions.assertEquals(2L, quantidade);
    }

    @Test
    void testRemoverPorCpf() {
        alunoService.removerPorCpf(aluno.getCpf());

        Mockito.verify(alunoRepository, Mockito.times(1)).deleteByCpf(ArgumentMatchers.anyLong());
    }

    @Test
    void testBuscarPorNome() {
        List<Aluno> alunoList = Arrays.asList(aluno);
        Mockito.when(alunoRepository.findByNomeContainingIgnoreCase(ArgumentMatchers.anyString())).thenReturn(alunoList);

        List<Aluno> alunosListByService = alunoService.buscarPorNome(aluno.getNome());

        Mockito.verify(alunoRepository, Mockito.times(1)).findByNomeContainingIgnoreCase(aluno.getNome());
    }
}

