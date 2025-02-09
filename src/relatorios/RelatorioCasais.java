package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;

import java.util.List;
import java.util.ArrayList;

public class RelatorioCasais {
    List<Casal> casais = new ArrayList<>();

    public RelatorioCasais(List<Casal> casais) {
        this.casais = casais;
    }

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

        // Ordena casais por total gasto de formar decrecente
        lares.sort((l1, l2) -> l2.casal.totalGasto.compareTo(l1.casal.totalGasto));

        // Imprime casais
        for (Lar lar : lares) {
            System.out.println(lar.casal.conjuge1.nome + ";" + lar.casal.conjuge2.nome + ";" + lar.casal.totalGasto
                    + ";" + lar.casal.numCasamentos);
        }

    }

}
