package com.itau.escolaItauSpring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.escolaItauSpring.helper.AlunoHelper;
import com.itau.escolaItauSpring.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    AlunoService alunoService;

    @Test
    void testAluno() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/aluno")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    void testCadastrar() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders
//                .post("/aluno")
//                .content(objectMapper.writeValueAsString(AlunoHelper.criarAluno()))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
//
//    }

    @Test
    void testBuscarAluno() throws Exception {
        var id = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/aluno/id/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAtivarAluno() throws Exception {
        var id = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders
                .patch("/aluno/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void testListarQuantidadeAtivos() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/aluno/quantidade-ativo")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRemoverPorCpf() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/aluno/cpf/{cpf}", 12345678900l)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testBuscarPorNome() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/aluno/busca/{nome}", "Daiane")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}