
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;
import src.relatorios.*;

import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        List<Pessoa> pessoas = null;
        List<Casal> casais = new ArrayList<>();
        List<Lar> lares = null;
        List<Casamento> casamentos = null;


        String caminho = args[0];
        //args[0];
        //"./01/";

        try {
            pessoas = LeitorCVS.lerPessoas(caminho + "pessoas.csv");
            lares = LeitorCVS.lerLares(caminho + "lares.csv", pessoas, casais);
            LeitorCVS.lerTarefas(caminho + "tarefas.csv", pessoas, lares);
            casamentos = LeitorCVS.lerCasamentos(caminho + "casamentos.csv", casais);
            LeitorCVS.lerFestas(caminho + "festas.csv", casais);
            LeitorCVS.lerCompras(caminho + "compras.csv", lares, pessoas);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RelatorioPrestadores.geraRelatorioPrestadores(pessoas);
        System.out.println("//////////////////////////////////////////////////////");
        RelatorioCasais.geraRelatorioCasais(casamentos, casais);
        System.out.println("//////////////////////////////////////////////////////");
        RelatorioPlanejamento.geraRelatorioPlanejamento(casais);
    }
}
