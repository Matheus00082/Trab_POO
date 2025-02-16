package src.organizacao;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;

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

    public int obtemMaiorNumParcelasTarefas(Date data) {
        int num = 0;

        for (Tarefa tarefa : this.tarefas.values()) {
            int numParcelasTarefas = tarefa.getParcelasInicial();

            if (numParcelasTarefas > num) {
                num = numParcelasTarefas;
                data = tarefa.getDataInicio();
            }

            if (tarefa.getCompras().size() > 0) {
                int numParcelasCompras = tarefa.obtemMaiorNumParcelasCompras();
                
                if (numParcelasCompras > num) {
                    num = numParcelasCompras;
                    data = tarefa.getDataInicio();
                }
            }
        }

        return num;
    }

    public void pagaTarefas(Date data, Casal casal) {
        for (Tarefa tarefa : this.tarefas.values()) {
            tarefa.pagaTarefa(casal, data);
        }
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
