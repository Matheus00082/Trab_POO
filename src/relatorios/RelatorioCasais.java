package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;

import java.util.List;
import java.util.Map;

public class RelatorioCasais {

    public static void geraRelatorioCasais(Map<String, Casamento> casamentos, List<Casal> casais) {
        // Calcula numero de casamentos que ambos foram
        for (Casal casal : casais) {

            casal.setNumCasamentos(Casamento.calculaNumCasamentos(casamentos, casal));

            Festa festa = casal.getCasamento().getFesta();

            if (festa != null) {
                casal.setTotalGasto(festa.getPreco());
            }

            Lar lar = casal.getLar();

            if (lar != null) {
                Double gastos = 0.0;
                for (Tarefa tarefa : lar.getTarefas().values()) {
                    gastos += tarefa.calculaValorTotal();
                }

                casal.setTotalGasto(casal.getTotalGasto() + gastos);
            }
        }

        /*Ordena casais por total gasto de formar decrecente. Se houver empate, ordena por
         * nome do conjuge 1 e em ultimo em caso ordenar por quantidade de casamentos*/
        casais.sort((Casal c1, Casal c2) -> {
            if (c1.getTotalGasto() == c2.getTotalGasto()) {
                if (c1.getConjuge1().getNome().equals(c2.getConjuge1().getNome())) {
                    return c1.getNumCasamentos() - c2.getNumCasamentos();
                }
                return c1.getConjuge1().getNome().compareTo(c2.getConjuge1().getNome());
            }
            return (int)(c2.getTotalGasto() - c1.getTotalGasto());
        });

        for (Casal casal : casais) {
            System.out.println(casal.getConjuge1().getNome() + ";" + casal.getConjuge2().getNome() + ";" + casal.getTotalGasto()
                    + ";" + casal.getNumCasamentos());
        }
    }

}
