package com.bcopstein.Emprestimos;

import org.junit.jupiter.api.Test;
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
            "1000, -0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void jurosSimplesComSeguro(double valor, double taxa, int nroParcelas) {
        if ((valor < 0 || taxa < 0 || nroParcelas < 0)) {
            System.out.println("\n Valor negativo inserido!");
            return;
        }
        double txSeguro = calculoDeJuros.getTaxaSeguro();
        double expected = (valor * (taxa + txSeguro) * nroParcelas);
        double result = calculoDeJuros.jurosEmprestimoJurosSimples(valor, taxa, nroParcelas);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 0.032, 10",
            "1000, -0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void jurosSimplesSemSeguro(double valor, double taxa, int nroParcelas) {
        if ((valor < 0 || taxa < 0 || nroParcelas < 0)) {
            System.out.println("\n Valor negativo inserido!");
            return;
        }
        calculoDeJuros.setSeguro(false);
        double expected = (valor * taxa * nroParcelas);
        double result = calculoDeJuros.jurosEmprestimoJurosSimples(valor, taxa, nroParcelas);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 0.032, 10",
            "1000, -0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void jurosCompostoComSeguro(double valor, double taxa, int nroParcelas) {
        if(valor <= 0){
            System.out.println("\n The Value is invalid: " + valor);
            return;
        }
        if(taxa < 0){
            System.out.println("\n The Tax value is invalid:  " + taxa);
            return;
        }
        if(nroParcelas <= 0){
            System.out.println("\n The number of payments is invalid:  " + nroParcelas );
            return;
        }
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
            "1000, 0.032, 10",
            "1000, -0.032, 10",
            "1000, 0, 10",
            "1000, 0.032, 1",
            "1000, 0.032, 0",
            "1000, 0.032, -1",
            "-1000, 0.032, 10",
            "0, 0.032, 10",
    })
    void jurosCompostoSemSeguro(double valor, double taxa, int nroParcelas) {
        if(valor <= 0){
            System.out.println("\n The Value is invalid: " + valor);
            return;
        }
        if(taxa < 0){
            System.out.println("\n The Tax value is invalid:  " + taxa);
            return;
        }
        if(nroParcelas <= 0){
            System.out.println("\n The number of payments is invalid:  " + nroParcelas );
            return;
        }
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
}
