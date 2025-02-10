package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;

import java.util.List;

public class RelatorioCasais {

    public static void geraRelatorioCasais(List<Casamento> casamentos, List<Casal> casais) {
        // Calcula numero de casamentos que ambos foram
        for (Casal casal : casais) {

            casal.numCasamentos = Casamento.calculaNumCasamentos(casamentos, casal);

            if (casal.casamento.festa != null) {
                casal.totalGasto = casal.casamento.festa.preco;
            }

            if (casal.lar != null) {
                for (Tarefa tarefa : casal.lar.tarefas) {
                    casal.totalGasto += tarefa.calculaValorTotal();
                }
            }
        }

        /*Ordena casais por total gasto de formar decrecente. Se houver empate, ordena por
         * nome do conjuge 1 e em ultimo em caso ordenar por quantidade de casamentos*/
        casais.sort((Casal c1, Casal c2) -> {
            if (c1.totalGasto == c2.totalGasto) {
                if (c1.conjuge1.nome.equals(c2.conjuge1.nome)) {
                    return c1.numCasamentos - c2.numCasamentos;
                }
                return c1.conjuge1.nome.compareTo(c2.conjuge1.nome);
            }
            return (int)(c2.totalGasto - c1.totalGasto);
        });

        for (Casal casal : casais) {
            System.out.println(casal.conjuge1.nome + ";" + casal.conjuge2.nome + ";" + casal.totalGasto
                    + ";" + casal.numCasamentos);
        }
    }

}
