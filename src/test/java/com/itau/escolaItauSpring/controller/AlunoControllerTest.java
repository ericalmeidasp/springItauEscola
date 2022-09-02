package com.itau.escolaItauSpring.controller;

import com.itau.escolaItauSpring.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlunoService alunoService;

    @Test
    void testAluno() throws Exception {
       var result = this.mockMvc.perform(MockMvcRequestBuilders.get("/aluno")).andReturn();
        assertEquals(HttpStatus.OK, result.getResponse().getStatus());
    }

    @Test
    void testCadastrar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/aluno")
        );
    }

    @Test
    void testBuscarAluno() throws Exception {
        var id = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/aluno/id/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }
}