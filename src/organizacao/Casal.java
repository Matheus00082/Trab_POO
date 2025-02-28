package src.organizacao;

import src.pessoas.PessoaFisica;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.text.ParseException;

import src.auxiliares.GerenciadorDeDatas;

import java.util.Date;

public class Casal {
    private PessoaFisica conjuge1, conjuge2;
    private Lar lar;
    private Casamento casamento;
    private Double totalGasto, saldoAtual;
    private int numCasamentos, duracaoParcelas;
    private Vector<Double> saldos;
    private static Map<String, Casal> casais = new HashMap<>();

    public Casal(PessoaFisica p1, PessoaFisica p2) {

        // Ordena os conjuges em ordem alfabética

        if (p1.getNome().compareTo(p2.getNome()) <= 0) {
            this.conjuge1 = p1;
            this.conjuge2 = p2;
        } else {
            this.conjuge1 = p2;
            this.conjuge2 = p1;
        }

        this.totalGasto = 0.0;
        this.numCasamentos = 0;
        this.casamento = null;
        this.lar = null;
        this.saldoAtual = p1.getDinheiroGuardado() + p2.getDinheiroGuardado();
        this.saldos = new Vector<>();
        this.saldos.add(this.saldoAtual);

        casais.put(this.conjuge1.getCPF() + this.conjuge2.getCPF(), this);
    }

    public boolean ehIgualCasal(String cpf1, String cpf2) {
        if ((this.conjuge1.getCPF().equals(cpf1) && this.conjuge2.getCPF().equals(cpf2)) ||
                (this.conjuge2.getCPF().equals(cpf1) && this.conjuge2.getCPF().equals(cpf2))) {
            return true;
        }

        return false;
    }

    public void calculaSaldosCasal() throws ParseException {
        Date dataAtual = this.calculaDataInicial();
        int numMeses = 0;

        if (dataAtual != null) {
            numMeses = this.calculaNumMeses(dataAtual);
        }

        this.duracaoParcelas = numMeses;

        for (int i = 1; i <= numMeses; i++) {
            this.saldoAtual *= (1.005);
            this.saldoAtual -= this.despesasMensais();

            if (this.casamento != null) {
                this.casamento.pagaFestaCasamento(dataAtual, this);
            }
            if (this.lar != null) {
                this.lar.pagaTarefas(dataAtual, this);
            }

            if (GerenciadorDeDatas.getMes(dataAtual) == 12) {
                this.saldoAtual += this.salarioTotal();
            }

            this.saldoAtual += this.salarioTotal();

            if (this.conjuge1.getEhPrestador() || this.conjuge2.getEhPrestador()) {
                Tarefa.pagaPessoaFisica(this, dataAtual);
            }

            // Adiciona novo saldo no vetor
            this.saldos.add(this.saldoAtual);
            dataAtual = GerenciadorDeDatas.incrementaMeses(dataAtual, 1);
        }
    }

    public int calculaNumMeses(Date dataInicial) throws ParseException {
        int numMeses = 0;

        // Calcula último mês de pagamentos

        Date dataFinal = null;

        if (this.casamento != null && this.casamento.getFesta() != null) {
            Date dataInicialFesta = this.casamento.getFesta().getData();
            int parcelasFesta = this.casamento.getFesta().getParcelasInicial();
            dataFinal = GerenciadorDeDatas.incrementaMeses(dataInicialFesta, parcelasFesta);
        } 

        if (this.lar != null && this.lar.getTarefas() != null) {
            for (Tarefa tarefa : this.lar.getTarefas().values()) {
                Date dataInicialTarefa = tarefa.getDataInicio();
                int parcelasTarefa = tarefa.getParcelasInicial();
                Date dataFinalTarefa = GerenciadorDeDatas.incrementaMeses(dataInicialTarefa, parcelasTarefa);

                if (dataFinal == null || (GerenciadorDeDatas.comparaDatas(dataFinalTarefa, dataFinal) > 0)) {
                    dataFinal = dataFinalTarefa;
                }

                if (tarefa.getCompras() != null) {
                    for (Compra compra : tarefa.getCompras()) {
                        int parcelasCompra = compra.getParcelas();
                        Date dataFinalCompra = GerenciadorDeDatas.incrementaMeses(dataInicialTarefa, parcelasCompra);
    
                        if (dataFinal == null || (GerenciadorDeDatas.comparaDatas(dataFinalCompra, dataFinal) > 0)) {
                            dataFinal = dataFinalCompra;
                        }
                    }
                }
            }
        }

        // Calcula num de meses entre as datas final e inicial

        numMeses = GerenciadorDeDatas.mesesEntreDatas(dataInicial, dataFinal);

        return  numMeses;
    }

    public Date calculaDataInicial() {
        Date data = null;

        if (this.casamento != null && this.casamento.getFesta() != null) {
            data = this.casamento.getFesta().getData();
        }

        if (this.lar != null && this.lar.getTarefas() != null) {
            for (Tarefa tarefa : this.lar.getTarefas().values()) {
                if (data == null || data.compareTo(tarefa.getDataInicio()) > 0) {
                    data = tarefa.getDataInicio();
                }
            }
        }

        return data;
    }

    public int getDuracaoParcelas() {
        return this.duracaoParcelas;
    }

    public Vector<Double> getSaldos() {
        return this.saldos;
    }

    public static Casal getCasalCadastrado(String p1, String p2) {
        return casais.get(p1 + p2);
    }

    public static Map<String, Casal> getCasais() {
        return casais;
    }

    public void calculaNumCasamentos(Map<String, Casamento> casamentos) {
        int num = 0;
        // Calcula numero de casamentos que ambos foram convidados
        for (Casamento c : casamentos.values()) {
            if (this.casamento != null) {
                // Verifica se o casal não é dono da festa
                if (c == this.casamento) {
                    continue;
                }
            }
            if (c.getFesta() != null && c.getFesta().casalFoiConvidado(this)) {
                num++;
            }
        }
        this.numCasamentos = num;
    }

    public void calculaTotalGasto() {
        Double totalGasto = 0.0;
        // Calcula o total gasto pelo casal com festa, tarefas e compras
        if (this.casamento != null && this.casamento.getFesta() != null) {
            totalGasto += this.casamento.getFesta().getPreco();
        }
        if (this.lar != null) {
            for (Tarefa tarefa : this.lar.getTarefas().values()) {
                totalGasto += tarefa.calculaValorTotalGastoTarefa();
            }
        }
        this.totalGasto = totalGasto;
    }

    public PessoaFisica getConjuge1() {
        return this.conjuge1;
    }

    public PessoaFisica getConjuge2() {
        return this.conjuge2;
    }

    public void setLar(Lar lar) {
        this.lar = lar;
    }

    public Lar getLar() {
        return this.lar;
    }

    public void setCasamento(Casamento casamento) {
        this.casamento = casamento;
    }

    public Casamento getCasamento() {
        return this.casamento;
    }

    public void setTotalGasto(Double totalGasto) {
        this.totalGasto = totalGasto;
    }

    public Double getTotalGasto() {
        return this.totalGasto;
    }

    public void setNumCasamentos(int numCasamentos) {
        this.numCasamentos = numCasamentos;
    }

    public int getNumCasamentos() {
        return this.numCasamentos;
    }

    public void addSaldoAtual(Double saldo) {
        this.saldoAtual += saldo;
    }

    public void subSaldoAtual(Double saldo) {
        this.saldoAtual -= saldo;
    }

    public Double getSaldo() {
        return this.saldoAtual;
    }

    public Double salarioTotal() {
        return this.conjuge1.getSalario() + this.conjuge2.getSalario();
    }

    public Double despesasMensais() {
        return this.conjuge1.getGastoMensal() + this.conjuge2.getGastoMensal();
    }
};