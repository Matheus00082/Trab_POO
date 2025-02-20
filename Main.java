
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;
import src.relatorios.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        //List<Pessoa> pessoas = null;
        Map<String, Pessoa> pessoas = null;
        List<Casal> casais = new ArrayList<>();
        Map<String, Lar> lares = null;
        Map<String, Casamento> casamentos = null;
        Map<String, Tarefa> tarefas = null;
        LeitorCVS.criaArquivosVazios();


        String caminho = "./Casos_exc/EXC18/";
        //"./Casos_exc/EXC18/";
        //args[0];
        //"./01/";

        try {
            pessoas = LeitorCVS.lerPessoas(caminho + "pessoas.csv");
            lares = LeitorCVS.lerLares(caminho + "lares.csv", pessoas, casais);
            tarefas = LeitorCVS.lerTarefas(caminho + "tarefas.csv", pessoas, lares);
            casamentos = LeitorCVS.lerCasamentos(caminho + "casamentos.csv", pessoas, casais);
            LeitorCVS.lerFestas(caminho + "festas.csv", casamentos);
            LeitorCVS.lerCompras(caminho + "compras.csv", lares, pessoas, tarefas);

            RelatorioPlanejamento.geraRelatorioPlanejamento(casais);
            if (pessoas != null) {
                RelatorioPrestadores.geraRelatorioPrestadores(pessoas);
            } if (casamentos != null && casais != null) {
                RelatorioCasais.geraRelatorioCasais(casamentos, casais);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
