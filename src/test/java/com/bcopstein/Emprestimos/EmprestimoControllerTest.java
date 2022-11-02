package com.bcopstein.Emprestimos;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmprestimoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void JurosSimplesEndpointTeste() throws Exception {
        mvc.perform(get("/emprestimo/jurosSimples?valor=1000&parcelas=10&taxa=0.032"))
                .andExpect(status().isOk());
    }

    @Test
    void JurosCompostosEndpointTeste() throws Exception {
        mvc.perform(get("/emprestimo/jurosSimples?valor=1000&parcelas=10&taxa=0.032"))
                .andExpect(status().isOk());
    }


}
