package com.bcopstein.Emprestimos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.BeforeEach;
import java.lang.*;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculoDeJurosTests {
    CalculoDeJuros calculoDeJuros = null;

    @BeforeEach
    void setUp() {
        calculoDeJuros = new CalculoDeJuros();
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "100, 0.032, 10",
    })
    void shouldBeEqualJurosSimplesComSeguro(double valor, double taxa, int nroParcelas) {
        double txSeguro = calculoDeJuros.getTaxaSeguro();
        double expected = (valor * (taxa + txSeguro) * nroParcelas);
        double result = calculoDeJuros.jurosEmprestimoJurosSimples(valor, taxa, nroParcelas);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, -0.032, 10",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void shouldThrowExceptionJurosSimplesComSeguro(double valor, double taxa, int nroParcelas) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculoDeJuros.jurosEmprestimoJurosSimples(valor, taxa, nroParcelas));
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "100, 0.032, 10",
    })
    void shouldBeEqualJurosSimplesSemSeguro(double valor, double taxa, int nroParcelas) {
        calculoDeJuros.setSeguro(false);
        double expected = (valor * taxa * nroParcelas);
        double result = calculoDeJuros.jurosEmprestimoJurosSimples(valor, taxa, nroParcelas);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, -0.032, 10",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void shouldThrowExceptionJurosSimplesSemSeguro(double valor, double taxa, int nroParcelas) {
        calculoDeJuros.setSeguro(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculoDeJuros.jurosEmprestimoJurosSimples(valor, taxa, nroParcelas));
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "100, 0.032, 10",
    })
    void shouldBeEqualJurosCompostoComSeguro(double valor, double taxa, int nroParcelas) {
        double result = calculoDeJuros.jurosEmprestimoJurosCompostos(valor, taxa, nroParcelas);
        double txSeguro = calculoDeJuros.getTaxaSeguro() + taxa;
        double valorAcum = valor;
        int parcelas = nroParcelas;
        while (parcelas > 0) {
            valorAcum += valorAcum * txSeguro;
            parcelas--;
        }
        double expected = valorAcum - valor;

        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, -0.032, 10",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void shouldThrowExceptionJurosCompostoComSeguro(double valor, double taxa, int nroParcelas) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculoDeJuros.jurosEmprestimoJurosCompostos(valor, taxa, nroParcelas));
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "100, 0.032, 10",
    })
    void shouldBeEqualJurosCompostoSemSeguro(double valor, double taxa, int nroParcelas) {
        calculoDeJuros.setSeguro(false);
        double result = calculoDeJuros.jurosEmprestimoJurosCompostos(valor, taxa, nroParcelas);
        double tx = taxa;
        double valorAcum = valor;
        int parcelas = nroParcelas;
        while (parcelas > 0) {
            valorAcum += valorAcum * tx;
            parcelas--;
        }
        double expected = valorAcum - valor;

        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, -0.032, 10",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void shouldThrowExceptionJurosCompostoSemSeguro(double valor, double taxa, int nroParcelas) {
        calculoDeJuros.setSeguro(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculoDeJuros.jurosEmprestimoJurosCompostos(valor, taxa, nroParcelas));
    }
}