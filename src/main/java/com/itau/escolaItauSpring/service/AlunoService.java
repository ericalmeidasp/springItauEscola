package com.itau.escolaItauSpring.service;

import com.itau.escolaItauSpring.config.mapper.AlunoMapper;
import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.exception.ItemNaoExistenteException;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoMapper mapper;
    private final AlunoRepository repository;

    public AlunoResponse adicionar(AlunoRequest alunoRequest) {
        Aluno aluno = mapper.toModel(alunoRequest);
        return mapper.toResponse(repository.save(aluno));
    }

    public void ativar(UUID id) {
        Aluno aluno = repository.findById(id).orElseThrow(ItemNaoExistenteException::new);
        aluno.setAtivado(true);
        repository.save(aluno);
    }

    public void desativar(UUID id) {
        Aluno aluno = repository.findById(id).orElseThrow(ItemNaoExistenteException::new);
        aluno.setAtivado(false);
        repository.save(aluno);
    }

    public List<AlunoResponse> listar() {
        return mapper.mapAluno(repository.findAll());
    }

    public AlunoResponse localizar(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(ItemNaoExistenteException::new));
    }

    public Long quantidadeAlunosAtivo() {
        return repository.countAlunoByAtivado(true);
    }

    public void removerPorCpf(Long cpf) {
        repository.deleteByCpf(cpf);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

}
