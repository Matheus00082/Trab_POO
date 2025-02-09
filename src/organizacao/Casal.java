package src.organizacao;

//import java.util.List;

import src.pessoas.PessoaFisica;

public class Casal {
    public PessoaFisica conjuge1, conjuge2;
    public Casamento casamento;
    public Double totalGasto;
    public int numCasamentos;

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
    }

}
