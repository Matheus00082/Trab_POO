
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;
import src.relatorios.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Pessoa> pessoas = new ArrayList<>();
        List<Lar> lares = new ArrayList<>();
        List<Tarefa> tarefas = new ArrayList<>();
        List<Casamento> casamentos = new ArrayList<>();

        String caminho = args[0];

        try {
            pessoas = LeitorCVS.lerPessoas(caminho + "pessoas.csv");
            lares = LeitorCVS.lerLares(caminho + "lares.csv", pessoas);
            tarefas = LeitorCVS.lerTarefas(caminho + "tarefas.csv", pessoas, lares);
            casamentos = LeitorCVS.lerCasamentos(caminho + "casamentos.csv", pessoas);
            LeitorCVS.lerFestas(caminho + "festas.csv", casamentos);
            LeitorCVS.lerCompras(caminho + "compras.csv", tarefas, pessoas);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RelatorioCasais.geraRelatorioCasais(casamentos, lares, tarefas);

    }
}
