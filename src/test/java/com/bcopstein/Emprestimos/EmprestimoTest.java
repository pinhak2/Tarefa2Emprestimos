package com.bcopstein.Emprestimos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
        "1000.0,0.032,10,1480.0"
    })
    void custoTotalComSeguroSimples(double valor, double taxa, int parcelas, double resultado) {
        emprestimo.setValor(valor);
        emprestimo.setTaxa(taxa);
        emprestimo.setNroParcelas(parcelas);
        emprestimo.setSegurado(true);
        emprestimo.setJurosCompostos(false);

        when(calculoJuros.jurosEmprestimoJurosSimples(valor, taxa, parcelas))
            .thenReturn(420.0);
        double valorObtido = emprestimo.custoTotal();
        Assertions.assertEquals(resultado, valorObtido);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
        "1000.0,0.032,10,1060.0"
    })
    void custoTotalComSeguroComposto(double valor, double taxa, int parcelas, double resultado) {
        emprestimo.setValor(valor);
        emprestimo.setTaxa(taxa);
        emprestimo.setNroParcelas(parcelas);
        emprestimo.setSegurado(true);
        emprestimo.setJurosCompostos(true);


        double valorObtido = emprestimo.custoTotal();
        double valorEsperado = resultado;
        Assertions.assertEquals(valorEsperado, valorObtido);
    }
}
