package src.organizacao;

import src.pessoas.PessoaFisica;

public class Casal {
    public PessoaFisica conjuge1, conjuge2;
    public float totalGasto;
    public int numCasamentos;

    public Casal(PessoaFisica p1, PessoaFisica p2) {
        this.conjuge1 = p1;
        this.conjuge2 = p2;
    }

}
