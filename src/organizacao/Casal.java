package src.organizacao;

//import java.util.List;

import src.pessoas.PessoaFisica;
import src.auxiliares.DataFunctions;
import src.auxiliares.Tarefa;
import java.util.Date;

public class Casal {
    private PessoaFisica conjuge1, conjuge2;
    private Lar lar;
    private Casamento casamento;
    private Double totalGasto;
    private int numCasamentos;
    private Double saldoAtual;

    public Casal(PessoaFisica p1, PessoaFisica p2) {
        
        // Em ordem alfabetica
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
        this.saldoAtual = p1.getPoupanca() + p2.getPoupanca();
    }

    public boolean ehIgualCasal(String CPF1, String CPF2) {
        if ((this.conjuge1.getCPF().equals(CPF1) && this.conjuge2.getCPF().equals(CPF2)) ||
            (this.conjuge1.getCPF().equals(CPF2) && this.conjuge2.getCPF().equals(CPF1)))
            return true;

        return false;
    }

    public Date calculaDataInicioPagamentos() {
        Date data = null;

        if (this.casamento != null) {
            if (this.casamento.getFesta() != null){
                data = this.casamento.getFesta().getData();
            }
        }

        if (this.lar != null) {
            if (this.lar.getTarefas().size() > 0) {
                for (Tarefa tarefa : this.lar.getTarefas().values()) {
                    if (data == null) {
                        data = tarefa.getDataInicio();
                    } else if (data.compareTo(tarefa.getDataInicio()) > 0) {
                        data = tarefa.getDataInicio();
                    }
                }
            }
        }

        return data;
    }

    public int calculaNumMeses() {
        int numMeses = 0;

        if (this.casamento != null) {
            numMeses = this.casamento.getFesta().getParcelasInicial();
        }

        if (this.lar.getTarefas().size() > 0) {
            if (this.lar.obtemMaiorNumParcelasTarefas() > numMeses) {
                numMeses = this.lar.obtemMaiorNumParcelasTarefas();
            }
        }

        return numMeses;
    }

    public void atualizaSaldoCasal(Date data) {
        this.saldoAtual += this.rendimentoPoupanca();
        this.saldoAtual -= this.despesasMensais();
        this.casamento.pagaFestaCasamento(data, this);
        this.lar.pagaTarefas(data, this);

        if (DataFunctions.getMes(data) == 12) {
            this.saldoAtual += this.salarioTotal() * 2;
        } else {
            this.saldoAtual += this.salarioTotal();
        }
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

    public Double rendimentoPoupanca() {
        return ((0.5 / 100) * this.saldoAtual);
    }

}
