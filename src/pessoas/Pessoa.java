package src.pessoas;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class Pessoa {
    protected String nome, endereco, telefone, id;
    protected Double lucro;
    protected String tipo;
    protected boolean ehPrestador = false;
    protected static Map<String, String> ids = new HashMap<>();

    public Pessoa(String nome, String endereco, String telefone, String id, String tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.id = id;
        this.tipo = tipo;
        this.lucro = 0.0;

        if (ids.get(id) != null) {
            throw new IllegalArgumentException("ID repetido " + id + " na classe Pessoa.");
        }
    }

    public static int compararTipos(String tipo1, String tipo2) {
        List<String> ordemTipos = Arrays.asList("PF", "PJ", "Loja");
        return Integer.compare(ordemTipos.indexOf(tipo1), ordemTipos.indexOf(tipo2));
    }

    public void setEhPrestador(boolean ehPrestador) {
        this.ehPrestador = ehPrestador;
    }

    public boolean getEhPrestador() {
        return ehPrestador;
    }

    public static void setIds(String id) {
        ids.put(id, id);
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
