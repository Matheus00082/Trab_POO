package src.auxiliares;

import src.organizacao.Casal;
import src.pessoas.*;
import src.organizacao.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

public class Tarefa {
    private String idTarefa;
    private Date dataInicio;
    private int prazoDias, parcelasInicial, parcelasAtual;
    private List<Compra> compras;
    private Pessoa trabalhador;
    private Double valorParcelado;
    protected static Map<String, String> ids = new HashMap<>();

    public Tarefa(String idTarefa, String dataInicio, int prazoDias, int parcelas, Double valor, Pessoa trabalhador) {
        this.idTarefa = idTarefa;

        try {
            this.dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.prazoDias = prazoDias;
        this.parcelasInicial = parcelas;
        this.parcelasAtual = parcelas;
        this.compras = new ArrayList<Compra>();
        this.trabalhador = trabalhador;
        this.trabalhador.setLucro(valor);
        this.valorParcelado = valor / parcelasInicial;

        if (ids.get(idTarefa) != null) {
            throw new IllegalArgumentException("ID repetido " + idTarefa + " na classe Tarefa.");
        }
    }

    public static void prestadorEstaCadastrado(Map<String, Pessoa> pessoas, String idPrestador, String idTarefa) {
        if (pessoas.get(idPrestador) == null) {
            throw new IllegalArgumentException("ID(s) de Prestador de Serviço " + idPrestador + " não cadastrado na Tarefa de ID " + idTarefa + ".");
        }
    }

    public static void tarefaTemIdDeLarValido(Map<String, Lar> lares, String idLar, String idTarefa) {
        if (lares.get(idLar) == null) {
            throw new IllegalArgumentException("ID de Lar " + idLar + " não cadastrado na Tarefa de ID " + idTarefa + ".");
        }
    }

    public static void setIds(String id) {
        ids.put(id, id);
    }

    public int obtemMaiorNumParcelasCompras() {
        int num = 0;

        for (Compra compra : this.compras) {
            if (compra.getParcelas() > num) {
                num = compra.getParcelas();
            }
        }

        return num;
    }

    public boolean ehMesdePagamentoTarefa(Date data) {
        if ((DataFunctions.ehMesmoMeseAno(data, this.dataInicio) || (this.parcelasAtual != this.parcelasInicial)) &&
                (this.parcelasAtual != 0)) {
            return true;
        }
        return false;
    }

    public void pagaTarefa(Casal casal, Date data) {
        boolean ehMes = ehMesdePagamentoTarefa(data);
        if (ehMes) {
            casal.subSaldoAtual(this.valorParcelado);
            this.parcelasAtual--;
        }

        if (DataFunctions.verificaSeEhPosterior(this.dataInicio, data)) {
            for (Compra compra : this.compras) {
                compra.pagaCompra(casal);
            }
        }
        
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

    public void setParcelasAtual(int parcelas) {
        this.parcelasAtual = parcelas;
    }

    public int getParcelasInicial() {
        return parcelasInicial;
    }

    public int getParcelasAtual() {
        return parcelasAtual;
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
