package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;

import java.util.List;

public class RelatorioCasais {

    public static void geraRelatorioCasais(List<Casamento> casamentos, List<Lar> lares) {
        // Calcula numero de casamentos que ambos foram
        for (Lar lar : lares) {

            lar.casal.numCasamentos = Casamento.calculaNumCasamentos(casamentos, lar.casal);

            if (lar.casamento.festa != null) {
                lar.casal.totalGasto = lar.casamento.festa.preco;
            }

            for (Tarefa tarefa : lar.tarefas) {
                lar.casal.totalGasto += tarefa.calculaValorTotal();
            }
        }

        /*Ordena casais por total gasto de formar decrecente. Se houver empate, ordena por
         * nome do conjuge 1 e em ultimo em caso ordenar por quantidade de casamentos*/
        lares.sort((Lar l1, Lar l2) -> {
            if (l1.casal.totalGasto == l2.casal.totalGasto) {
                if (l1.casal.conjuge1.nome.equals(l2.casal.conjuge1.nome)) {
                    return l1.casal.numCasamentos - l2.casal.numCasamentos;
                }
                return l1.casal.conjuge1.nome.compareTo(l2.casal.conjuge1.nome);
            }
            return (int)(l2.casal.totalGasto - l1.casal.totalGasto);
        });

        for (Lar lar : lares) {
            System.out.println(lar.casal.conjuge1.nome + ";" + lar.casal.conjuge2.nome + ";" + lar.casal.totalGasto
                    + ";" + lar.casal.numCasamentos);
        }

    }

}
