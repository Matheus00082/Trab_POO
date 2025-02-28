package src.organizacao;

import java.util.Date;
import src.pessoas.Pessoas;
import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import src.auxiliares.GerenciadorDeDatas;

public class Tarefa {
    private String idTarefa;
    private Date dataInicio;
    private int prazoDias, parcelasInicial, parcelasAtual;
    private List<Compra> compras;
    private Pessoas trabalhador;
    private Double valorParcelado, valorTotal;
    private static Map<String, Tarefa> tarefas = new HashMap<>();

    public Tarefa(String idTarefa, Pessoas trabalhador, String dataInicio, int prazoDias, Double valor, int parcelas)
            throws ParseException {

        if (tarefas.get(idTarefa) != null) {
            throw new IllegalArgumentException("ID repetido " + idTarefa + " na classe Tarefa.");
        }

        tarefas.put(idTarefa, this);

        try {
            this.dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio.replace(" ", ""));
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }

        this.idTarefa = idTarefa;
        this.trabalhador = trabalhador;
        this.prazoDias = prazoDias;
        this.parcelasInicial = parcelas;
        this.parcelasAtual = parcelas;
        this.valorTotal = valor;
        this.valorParcelado = valor / parcelas;
        this.compras = new ArrayList<>();
    }

    public static void pagaPessoaFisica(Casal casal, Date data) throws ParseException {
        String idConjuge1 = casal.getConjuge1().getId(), idConjuge2 = casal.getConjuge2().getId(), prestador = null;

        for (Tarefa tarefa : tarefas.values()) {
            prestador = tarefa.getTrabalhador().getId();
            boolean ehMes = tarefa.ehMesPagamentoTarefa(data);
            if (ehMes) {
                if (prestador.equals(idConjuge1) || prestador.equals(idConjuge2)) {
                    casal.addSaldoAtual(tarefa.valorParcelado);
                }
            }
        }
    }

    public static void prestadorEstaCadastrado(String idPrestador, String idTarefa) {
        Map<String, Pessoas> pessoas = Pessoas.getPessoas();

        if (pessoas.get(idPrestador) == null) {
            throw new IllegalArgumentException("ID(s) de Prestador de Serviço " + idPrestador
                    + " não cadastrado na Tarefa de ID " + idTarefa + ".");
        }
    }

    public static void tarefaTemIdDeLarValido(String idLar, String idTarefa) {
        Map<String, Lar> lares = Lar.getLares();

        if (lares.get(idLar) == null) {
            throw new IllegalArgumentException(
                    "ID(s) de Lar " + idLar + " não cadastrado na Tarefa de ID " + idTarefa + ".");
        }
    }

    public boolean ehMesPagamentoTarefa(Date data) throws ParseException {
        if (GerenciadorDeDatas.ehMesmoMeseAno(data, this.dataInicio)
                || ((this.parcelasAtual < this.parcelasInicial) && (this.parcelasAtual > 0))) {
            return true;
        }
        return false;
    }

    public void pagaTarefa(Casal casal, Date data) throws ParseException {
        boolean ehMes = ehMesPagamentoTarefa(data);
        if (ehMes) {
            casal.subSaldoAtual(this.valorParcelado);
            this.parcelasAtual--;
        }

        if (GerenciadorDeDatas.comparaDatas(this.dataInicio, data) < 0) {
            for (Compra compra : this.compras) {
                compra.pagaCompra(casal);
            }
        }
    }

    public Double obtemValorTotalCompras(List<Compra> compras) {
        Double total = 0.0;
        for (Compra compra : compras) {
            total += (compra.getPreco() * compra.getQuantidade());
        }

        return total;
    }

    public Double calculaValorTotalGastoTarefa() {
        Double valorTotal = 0.0;

        valorTotal += obtemValorTotalCompras(this.compras);

        return (valorTotal + this.valorTotal);
    }

    public void calculaLucroPrestadores() {
        if (this.trabalhador.getTipo().equals("PJ") || this.trabalhador.getTipo().equals("PF")) {
            this.trabalhador.addLucro(this.valorTotal);
        }

        for (Compra compra : this.compras) {
            compra.getLoja().addLucro(compra.getPreco() * compra.getQuantidade());
        }
    }

    public static Map<String, Tarefa> getTarefas() {
        return tarefas;
    }

    public static Tarefa getTarefaCadastrada(String idTarefa) {
        return tarefas.get(idTarefa);
    }

    public void setIdTarefa(String idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getIdTarefa() {
        return this.idTarefa;
    }

    public void setDataInicio(String dataInicio) throws ParseException {
        try {
            this.dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio);
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }
    }

    public Date getDataInicio() {
        return this.dataInicio;
    }

    public void setPrazoDias(int prazoDias) {
        this.prazoDias = prazoDias;
    }

    public int getPrazoDias() {
        return this.prazoDias;
    }

    public void setParcelasAtual(int parcelas) {
        this.parcelasAtual = parcelas;
    }

    public int getParcelasInicial() {
        return this.parcelasInicial;
    }

    public int getParcelasAtual() {
        return this.parcelasAtual;
    }

    public void addCompra(Compra compra) {
        this.compras.add(compra);
    }

    public List<Compra> getCompras() {
        return this.compras;
    }

    public void setTrabalhador(Pessoas trabalhador) {
        this.trabalhador = trabalhador;
    }

    public Pessoas getTrabalhador() {
        return this.trabalhador;
    }

    public void setValorParcelado(Double valorParcelado) {
        this.valorParcelado = valorParcelado;
    }

    public Double getValorParcelado() {
        return this.valorParcelado;
    }
}