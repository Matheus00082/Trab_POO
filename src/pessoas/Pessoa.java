package src.pessoas;

public class Pessoa {
    protected String nome, endereco, telefone, id;
    protected Double lucro;
    protected String tipo;

    public Pessoa(String nome, String endereco, String telefone, String id, String tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.id = id;
        this.tipo = tipo;
        this.lucro = 0.0;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLucro(Double lucro) {
        this.lucro += lucro;
    }

    public Double getLucro() {
        return lucro;
    }

    public String getTipo() {
        return tipo;
    }

}
