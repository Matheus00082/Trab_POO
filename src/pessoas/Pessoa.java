package src.pessoas;

public class Pessoa {
    public String nome, endereco, telefone, id;
    public char tipo;

    public Pessoa(String nome, String endereco, String telefone, String id, char tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.id = id;
        this.tipo = tipo;
    }

}
