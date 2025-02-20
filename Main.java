
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;
import src.relatorios.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Pessoa> pessoas = null;
        List<Casal> casais = new ArrayList<>();
        Map<String, Lar> lares = null;
        Map<String, Casamento> casamentos = null;
        Map<String, Tarefa> tarefas = null;

        String caminho = args[0];

        try {
            LeitorCVS.criaArquivosVazios(caminho);
            pessoas = LeitorCVS.lerPessoas(caminho + "pessoas.csv");
            lares = LeitorCVS.lerLares(caminho + "lares.csv", pessoas, casais);
            tarefas = LeitorCVS.lerTarefas(caminho + "tarefas.csv", pessoas, lares);
            casamentos = LeitorCVS.lerCasamentos(caminho + "casamentos.csv", pessoas, casais);
            LeitorCVS.lerFestas(caminho + "festas.csv", casamentos);
            LeitorCVS.lerCompras(caminho + "compras.csv", lares, pessoas, tarefas);

            RelatorioPlanejamento.geraRelatorioPlanejamento(caminho, casais);
            if (pessoas != null) {
                RelatorioPrestadores.geraRelatorioPrestadores(caminho, pessoas);
            } if (casamentos != null && casais != null) {
                RelatorioCasais.geraRelatorioCasais(caminho, casamentos, casais);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } 
    }

}
