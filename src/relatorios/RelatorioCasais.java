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

    public static void geraRelatorioCasais(List<Casamento> casamentos, List<Lar> lares, List<Tarefa> tarefas) {

        // Calcula numero de casamentos que ambos foram
        for (Casamento casamento : casamentos) {
            casamento.casal.numCasamentos = Casamento.calculaNumCasamentos(casamentos, casamento.casal);
            System.out.println(casamento.casal.conjuge1.nome + " " + casamento.casal.conjuge2.nome);
        }

    }


}
