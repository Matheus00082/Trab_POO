package src.organizacao;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;

//import src.pessoas.PessoaFisica;
import src.pessoas.Pessoa;

public class Lar {
    private String idLar, rua, complemento;
    private Map<String, Tarefa> tarefas;
    private int numero;
    protected static Map<String, String> ids = new HashMap<>();

    public Lar(String idLar, String rua, int numero, String complemento) {
        this.idLar = idLar;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.tarefas = new HashMap<>();

        if (ids.get(idLar) != null) {
            throw new IllegalArgumentException("ID repetido " + idLar + " na classe Lar.");
        }
    }

    public static void pessoaEstaCadastradaLar(Map<String, Pessoa> pessoas, String id1, String id2, String idLar) {
        if (pessoas.get(id1) == null && pessoas.get(id2) == null) {
            throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " " + id2 + " não cadastrado no Lar de ID " + idLar + ".");
        } else if (pessoas.get(id1) == null && pessoas.get(id2) != null) {
            throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " não cadastrado no Lar de ID " + idLar + ".");
        } else if (pessoas.get(id1) != null && pessoas.get(id2) == null) {
            throw new IllegalArgumentException("ID(s) de Pessoa " + id2 + " não cadastrado no Lar de ID " + idLar + ".");
        }
    }

    public static void setIds(String id) {
        ids.put(id, id);
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

    public void pagaTarefas(Date data, Casal casal) throws Exception {
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
