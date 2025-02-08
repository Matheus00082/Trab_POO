package src.organizacao;

import src.pessoas.PessoaFisica;

public class Lar {
    public String idLar, rua, complemento;
    public PessoaFisica conjuge1, conjuge2;
    public int numero;

    public Lar(String idLar, PessoaFisica conjuge1, PessoaFisica conjuge2, String rua, int numero, String complemento) {
        this.idLar = idLar;
        this.conjuge1 = conjuge1;
        this.conjuge2 = conjuge2;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }
}
