package src.auxiliares;

import src.pessoas.*;
import src.organizacao.Lar;

public class Tarefa {
    public String idTarefa, dataInicio;
    public int prazoDias, parcelas;
    public Compra[] compras;
    public Lar lar;
    public PessoaFisica trabalhadorFisico;
    public PessoaJuridica trabalhadorJuridico;

    public Tarefa(String idTarefa, String dataInicio, int prazoDias, int parcelas, Compra[] compras, Lar lar, PessoaFisica trabalhadorFisico, PessoaJuridica trabalhadorJuridico) {
        this.idTarefa = idTarefa;
        this.dataInicio = dataInicio;
        this.prazoDias = prazoDias;
        this.parcelas = parcelas;
        this.compras = compras;
        this.lar = lar;
        this.trabalhadorFisico = trabalhadorFisico;
        this.trabalhadorJuridico = trabalhadorJuridico;
    }
}
