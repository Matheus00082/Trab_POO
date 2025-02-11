package src.organizacao;

import java.util.Map;
import java.util.HashMap;

//import src.pessoas.PessoaFisica;
import src.auxiliares.*;

public class Lar {
    private String idLar, rua, complemento;
    private Map<String, Tarefa> tarefas;
    private int numero;

    public Lar(String idLar, String rua, int numero, String complemento) {
        this.idLar = idLar;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.tarefas = new HashMap<>();
    }

    public int calculaNumMeses(Casamento casamento) {
        int numMeses = casamento.getFesta().getParcelas();

        for (Tarefa tarefa : this.tarefas.values()) {
            int numParcelasTarefa = tarefa.getParcelas();
            if ( numParcelasTarefa > numMeses) {
                numMeses = numParcelasTarefa;
            }

            for (Compra compra : tarefa.getCompras()) {
                int numParcelasCompra = compra.getParcelas();
                if (numParcelasCompra > numMeses) {
                    numMeses = numParcelasCompra;
                }
            }
        }

        return numMeses;
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
