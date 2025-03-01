package src.pessoas;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class Pessoas {
    protected String nome, telefone, endereco, id, tipo;
    protected Double lucro;
    protected static Map<String, Pessoas> pessoas = new HashMap<>();
    protected boolean prestador = false;

    public Pessoas(String id, String tipo, String nome, String telefone, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.id = id;
        this.tipo = tipo;
        this.lucro = 0.0;

        if (pessoas.get(id) != null) {
            throw new IllegalArgumentException("ID repetido " + id + " na classe Pessoa.");
        }
    
        pessoas.put(id, this);
    }

    // Compara os tipos de pessoa para ordenar em ordem alfabética
    public static int compararTipos(String tipo1, String tipo2) {
        List<String> ordemTipos = Arrays.asList("PF", "PJ", "Loja");
        return Integer.compare(ordemTipos.indexOf(tipo1), ordemTipos.indexOf(tipo2));
    }

    public static Pessoas getPessoaCadastrada(String id) {
        return pessoas.get(id);
    }

    // Retorna a lista de pessoas cadastradas
    public static Map<String, Pessoas> getPessoas() {
        return pessoas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void addLucro(Double lucro) {
        this.lucro += lucro;
    }

    public Double getLucro() {
        return this.lucro;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setPrestador(boolean prestador) {
        this.prestador = prestador;
    }

    public boolean getEhPrestador() {
        return this.prestador;
    }

}