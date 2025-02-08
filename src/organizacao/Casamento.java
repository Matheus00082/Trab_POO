package src.organizacao;

import src.pessoas.PessoaFisica;

public class Casamento {
    public String idCasamento;
    public PessoaFisica conjuge1, conjuge2;
    public String data, hora, local;
    public Festa festa;

    public Casamento(String idCasamento, PessoaFisica conjuge1, PessoaFisica conjuge2, String data, String hora, String local, Festa festa) {
        this.idCasamento = idCasamento;
        this.conjuge1 = conjuge1;
        this.conjuge2 = conjuge2;
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.festa = festa;
    }

}
