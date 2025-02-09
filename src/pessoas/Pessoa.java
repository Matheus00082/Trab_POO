package src.pessoas;

public class Pessoa {
    public String nome, endereco, telefone, id;
    public Double lucro;
    public String tipo;

    public Pessoa(String nome, String endereco, String telefone, String id, String tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.id = id;
        this.tipo = tipo;
        this.lucro = 0.0;
    }

}
