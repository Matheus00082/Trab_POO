
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
        List<Casamento> casamentos = new ArrayList<>();

        String caminho = "./01/";

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

        RelatorioCasais.geraRelatorioCasais(casamentos, lares);
    }
}
