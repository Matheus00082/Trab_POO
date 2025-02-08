
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Pessoa> pessoas;
        List<Lar> lares;
        List<Tarefa> tarefas;
        List<Casamento> casamentos;

        try {
            pessoas = LeitorCVS.lerPessoas("./01/pessoas.csv");
            lares = LeitorCVS.lerLares("./01/lares.csv", pessoas);
            tarefas = LeitorCVS.lerTarefas("./01/tarefas.csv", pessoas, lares);
            casamentos = LeitorCVS.lerCasamentos("./01/casamentos.csv", pessoas);
            LeitorCVS.lerFestas("./01/festas.csv", casamentos);
            LeitorCVS.lerCompras("./01/compras.csv", tarefas, pessoas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
