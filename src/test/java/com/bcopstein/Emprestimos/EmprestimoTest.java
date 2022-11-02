package com.bcopstein.Emprestimos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EmprestimoTest {
    CalculoDeJuros calculoJuros = mock(CalculoDeJuros.class);
    Emprestimo emprestimo = null;

    @BeforeEach
    public void setup() {
        emprestimo = new Emprestimo.Builder(calculoJuros)
                    .valor(0)
                    .taxa(0)
                    .parcelas(0)
                    .build();
    }

    @ParameterizedTest
    @CsvSource({
        "1000,0.032,10"
    })
    void custoTotalComSeguro(double valor, double taxa, int parcelas) {
        emprestimo.setValor(valor);
        emprestimo.setTaxa(taxa);
        emprestimo.setNroParcelas(parcelas);
        emprestimo.setSegurado(true);

        double valorObtido = emprestimo.custoTotal();
        double valorEsperado = 1060.0;
        Assertions.assertEquals(valorEsperado, valorObtido);
    }

}
