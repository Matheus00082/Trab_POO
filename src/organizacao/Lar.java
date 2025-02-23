package src.organizacao;

import java.util.Map;

import src.pessoas.Pessoas;
import java.util.Date;
import java.text.ParseException;

import java.util.HashMap;

public class Lar {
    private String idLar, rua, complemento;
    private Map<String, Tarefa> tarefas;
    private int numero;
    private static Map<String, Lar> lares = new HashMap<>();

    public Lar(String idLar, String rua, int numero, String complemento) {

        if (lares.get(idLar) != null) {
            throw new IllegalArgumentException("ID repetido " + idLar + " na classe Lar.");
        }

        lares.put(idLar, this);

        this.idLar = idLar;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.tarefas = new HashMap<>();
    }

    public static void pessoaEstaCadastradaLar(String idLar, String id1, String id2) {
        Map<String, Pessoas> pessoas = Pessoas.getPessoas();

        if (pessoas.get(id1) == null && pessoas.get(id2) == null) {
             throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " " + id2 + " não cadastrado no Lar de ID " + idLar + ".");
        } else if (pessoas.get(id1) == null && pessoas.get(id2) != null) {
            throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " não cadastrado no Lar de ID " + idLar + ".");
        } else if (pessoas.get(id1) != null && pessoas.get(id2) == null) {
            throw new IllegalArgumentException("ID(s) de Pessoa " + id2 + " não cadastrado no Lar de ID " + idLar + ".");
        }
    }

    public void pagaTarefas(Date data, Casal casal) throws ParseException {
        for (Tarefa tarefa : this.tarefas.values()) {
            tarefa.pagaTarefa(casal, data);
        }
    }

    public static Map<String, Lar> getLares() {
        return lares;
    }

    public static Lar getLarCadastrado(String idLar) {
        return lares.get(idLar);
    }

    public void setidLar(String idLar) {
        this.idLar = idLar;
    }

    public String getidLar() {
        return this.idLar;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getRua() {
        return this.rua;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public void addTarefa(String key, Tarefa tarefa) {
        this.tarefas.put(key, tarefa);
    }

    public Map<String, Tarefa> getTarefas() {
        return this.tarefas;
    }

}