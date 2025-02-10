
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;
import src.relatorios.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Pessoa> pessoas = null;
        List<Lar> lares = null;
        List<Casamento> casamentos = null;


        String caminho = args[0];

        try {
            pessoas = LeitorCVS.lerPessoas(caminho + "pessoas.csv");
            lares = LeitorCVS.lerLares(caminho + "lares.csv", pessoas);
            LeitorCVS.lerTarefas(caminho + "tarefas.csv", pessoas, lares);
            casamentos = LeitorCVS.lerCasamentos(caminho + "casamentos.csv", lares);
            LeitorCVS.lerFestas(caminho + "festas.csv", lares);
            LeitorCVS.lerCompras(caminho + "compras.csv", lares, pessoas);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RelatorioPrestadores.geraRelatorioPrestadores(pessoas);
        System.out.println("//////////////////////////////////////////////////////");
        RelatorioCasais.geraRelatorioCasais(casamentos, lares);
        System.out.println("//////////////////////////////////////////////////////");
        RelatorioPlanejamento.geraRelatorioPlanejamento(lares);
    }
}
