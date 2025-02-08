
import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Pessoa> pessoas;
        List<Lar> lares;
        //List<Tarefa> tarefas;
        // List<Casamento> casamentos;

        try {
            pessoas = LeitorCVS.lerPessoas("./01/pessoas.csv");
            lares = LeitorCVS.lerLares("./01/lares.csv", pessoas);

            for (Pessoa pessoa : pessoas) {
                System.out.println(pessoa.id + " " + pessoa.nome + " " + pessoa.endereco + " " + pessoa.telefone);

                if (pessoa.tipo == 'F') {
                    PessoaFisica pf = (PessoaFisica) pessoa;
                    System.out.println(pf.CPF + " " + pf.datadeNascimento + " " + pf.salario + " " + pf.gastoMensal + " " + pf.poupanca);
                }
            }

            for (Lar lar : lares) {
                System.out.println(lar.idLar + " " + lar.rua + " " + lar.numero + " " + lar.complemento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
