package src.auxiliares;

import src.pessoas.*;
import java.util.List;
import java.util.ArrayList;

public class Tarefa {
    public String idTarefa, dataInicio;
    public int prazoDias, parcelas;
    public List<Compra> compras;
    public Pessoa trabalhador;
    public Double valorTotal;

    public Tarefa(String idTarefa, String dataInicio, int prazoDias, int parcelas, Double valor, Pessoa trabalhador) {
        this.idTarefa = idTarefa;
        this.dataInicio = dataInicio;
        this.prazoDias = prazoDias;
        this.parcelas = parcelas;
        this.compras = new ArrayList<Compra>();
        this.trabalhador = trabalhador;
        this.valorTotal = valor;
    }

    public Double calculaValorTotal() {
        Double valorTotal = 0.0;

        for (Compra compra : compras) {
            valorTotal += compra.preco * compra.quantidade;
        }

        return (valorTotal + this.valorTotal);
    } 
}
