package src.organizacao;

import java.util.List;
import java.util.ArrayList;

import src.pessoas.PessoaFisica;
import src.auxiliares.*;

public class Lar {
    public String idLar, rua, complemento;
    public Casal casal;
    public List<Tarefa> tarefas;
    public Casamento casamento;
    public int numero;

    public Lar(String idLar, PessoaFisica conjuge1, PessoaFisica conjuge2, String rua, int numero, String complemento) {
        this.idLar = idLar;
        this.casal = new Casal(conjuge1, conjuge2);
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.tarefas = new ArrayList<>();
        this.casamento = null;
    }

    public int calculaNumMeses() {
        int numMeses = this.casamento.festa.parcelas;

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
