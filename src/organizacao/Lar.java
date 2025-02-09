package src.organizacao;

import src.pessoas.PessoaFisica;

public class Lar {
    public String idLar, rua, complemento;
    public Casal casal;
    public int numero;

    public Lar(String idLar, PessoaFisica conjuge1, PessoaFisica conjuge2, String rua, int numero, String complemento) {
        this.idLar = idLar;
        this.casal = new Casal(conjuge1, conjuge2);
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }
}
