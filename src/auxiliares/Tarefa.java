package src.auxiliares;

import src.pessoas.*;
import src.organizacao.Lar;
import java.util.List;
import java.util.ArrayList;

public class Tarefa {
    public String idTarefa, dataInicio;
    public int prazoDias, parcelas;
    public List<Compra> compras;
    public Lar lar;
    public Pessoa trabalhador;
    public Double valorTotal;

    public Tarefa(String idTarefa, String dataInicio, int prazoDias, int parcelas, Double valor,Lar lar, Pessoa trabalhador) {
        this.idTarefa = idTarefa;
        this.dataInicio = dataInicio;
        this.prazoDias = prazoDias;
        this.parcelas = parcelas;
        this.compras = new ArrayList<Compra>();
        this.lar = lar;
        this.trabalhador = trabalhador;
        this.valorTotal = valor;
    }
}
