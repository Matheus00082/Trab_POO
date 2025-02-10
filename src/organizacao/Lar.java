package src.organizacao;

import java.util.List;
import java.util.ArrayList;

//import src.pessoas.PessoaFisica;
import src.auxiliares.*;

public class Lar {
    public String idLar, rua, complemento;
    public List<Tarefa> tarefas;
    public int numero;

    public Lar(String idLar, String rua, int numero, String complemento) {
        this.idLar = idLar;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.tarefas = new ArrayList<>();
    }

    public int calculaNumMeses(Casamento casamento) {
        int numMeses = casamento.festa.parcelas;

        for (Tarefa tarefa : this.tarefas) {
            if (tarefa.parcelas > numMeses) {
                numMeses = tarefa.parcelas;
            }

            for (Compra compra : tarefa.compras) {
                if (compra.parcelas > numMeses) {
                    numMeses = compra.parcelas;
                }
            }
        }

        return numMeses;
    }
}
