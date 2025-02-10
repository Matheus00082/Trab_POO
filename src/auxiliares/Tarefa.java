package src.auxiliares;

import src.pessoas.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Tarefa {
    private String idTarefa;
    private Date dataInicio;
    private int prazoDias, parcelas;
    private List<Compra> compras;
    private Pessoa trabalhador;
    private Double valorParcelado;

    public Tarefa(String idTarefa, String dataInicio, int prazoDias, int parcelas, Double valor, Pessoa trabalhador) {
        this.idTarefa = idTarefa;
            
        try {
            this.dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.prazoDias = prazoDias;
        this.parcelas = parcelas;
        this.compras = new ArrayList<Compra>();
        this.trabalhador = trabalhador;
        this.trabalhador.setLucro(valor);;
        this.valorParcelado = valor/parcelas;
    }

    public void setIdTarefa(String idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getIdTarefa() {
        return idTarefa;
    }

    public void setDataInicio(String dataInicio) {
        try {
            this.dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setPrazoDias(int prazoDias) {
        this.prazoDias = prazoDias;
    }

    public int getPrazoDias() {
        return prazoDias;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void addCompras(Compra compra) {
        this.compras.add(compra);
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setTrabalhador(Pessoa trabalhador) {
        this.trabalhador = trabalhador;
    }

    public Pessoa getTrabalhador() {
        return trabalhador;
    }

    public void setValorParcelado(Double valorParcelado) {
        this.valorParcelado = valorParcelado;
    }

    public Double getValorParcelado() {
        return valorParcelado;
    }

    public Double calculaValorTotal() {
        Double valorTotal = 0.0;

        for (Compra compra : compras) {
            valorTotal += compra.getLoja().getLucro();
        }

        return (valorTotal + this.trabalhador.getLucro());
    } 
}
