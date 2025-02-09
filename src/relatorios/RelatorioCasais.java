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

    public static List<Casal> criaCasais(List<Casamento> casamentos, List<Lar> lares) {
        List<Casal> casais = new ArrayList<>();

        for (Casamento casamento : casamentos) {
            if (casamento.conjuge1.nome.compareTo(casamento.conjuge2.nome) <= 0) {  // Em ordem alfabetica
                casais.add(new Casal(casamento.conjuge1, casamento.conjuge2));
            } else {
                casais.add(new Casal(casamento.conjuge2, casamento.conjuge1));
            }
        }

        boolean existeCasal = false;

        for (Lar lar : lares) {
            for (Casal casal : casais) {
                if ((lar.conjuge1 == casal.conjuge1 && lar.conjuge2 == casal.conjuge2) ||
                    (lar.conjuge1 == casal.conjuge2 && lar.conjuge2 == casal.conjuge1)) {
                        existeCasal = true;
                        break;
                }
            }
            if (!existeCasal) {
                if (lar.conjuge1.nome.compareTo(lar.conjuge2.nome) <= 0) {  // Em ordem alfabetica
                    casais.add(new Casal(lar.conjuge1, lar.conjuge2));
                } else {
                    casais.add(new Casal(lar.conjuge2, lar.conjuge1));
                }
            }
        }

        return casais;
    }

    public static void geraRelatorioCasais(List<Casamento> casamentos, List<Lar> lares, List<Tarefa> tarefas) {
        List<Casal> casais = criaCasais(casamentos, lares);

        // Calcula numero de casamentos que ambos foram

        for (Casal casal : casais) {
            casal.numCasamentos = Casamento.calculaNumCasamentos(casamentos, casal);
        }
        for (Casal casal : casais) {
            System.out.println(casal.conjuge1.nome + " " + casal.conjuge2.nome);
        }

    }


}
