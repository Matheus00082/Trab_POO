package src.organizacao;

//import java.util.List;

import src.pessoas.PessoaFisica;

public class Casal {
    public PessoaFisica conjuge1, conjuge2;
    public Lar lar;
    public Casamento casamento;
    public Double totalGasto;
    public int numCasamentos;
    public Double saldo;

    public Casal(PessoaFisica p1, PessoaFisica p2) {
        
        // Em ordem alfabetica
        if (p1.nome.compareTo(p2.nome) <= 0) {
            this.conjuge1 = p1;
            this.conjuge2 = p2;
        } else {
            this.conjuge1 = p2;
            this.conjuge2 = p1;
        }
        
        this.totalGasto = 0.0;
        this.numCasamentos = 0;
        this.casamento = null;
        this.saldo = p1.poupanca + p2.poupanca;
    }

    public boolean ehIgualCasal(String CPF1, String CPF2) {
        if ((this.conjuge1.CPF.equals(CPF1) && this.conjuge2.CPF.equals(CPF2)) ||
            (this.conjuge1.CPF.equals(CPF2) && this.conjuge2.CPF.equals(CPF1)))
            return true;

        return false;
    }

    public Double salarioTotal() {
        return this.conjuge1.salario + this.conjuge2.salario;
    }

    public Double despesasMensais() {
        return this.conjuge1.gastoMensal + this.conjuge2.gastoMensal;
    }

    public Double rendimentoPoupanca() {
        return ((0.5 / 100) * this.saldo);
    }

}
