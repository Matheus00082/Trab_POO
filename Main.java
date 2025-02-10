
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;
import src.relatorios.*;

import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {
        List<Pessoa> pessoas = null;
        List<Casal> casais = new ArrayList<>();
        List<Lar> lares = null;
        List<Casamento> casamentos = null;


        String caminho = "./01/";
        //args[0];
        //"./01/";

        try {
            pessoas = LeitorCVS.lerPessoas(caminho + "pessoas.csv");
            lares = LeitorCVS.lerLares(caminho + "lares.csv", pessoas, casais);
            LeitorCVS.lerTarefas(caminho + "tarefas.csv", pessoas, lares);
            casamentos = LeitorCVS.lerCasamentos(caminho + "casamentos.csv", casais);
            LeitorCVS.lerFestas(caminho + "festas.csv", casais);
            LeitorCVS.lerCompras(caminho + "compras.csv", lares, pessoas);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // imprime datas de nascimento e casamento mas as datas não estão formatadas como dd/MM/yyyy
            for (Casal casal : casais) {
                System.out.println(casal.getConjuge1().getNome() + " nasceu em " + sdf.format(casal.getConjuge2().getDatadeNascimento()) + " e casou em " +  sdf.format(casal.getCasamento().getData()));
                System.out.println(casal.getConjuge1().getNome() + " nasceu em " +  sdf.format(casal.getConjuge2().getDatadeNascimento()) + " e casou em " +  sdf.format(casal.getCasamento().getData()));
            }

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
