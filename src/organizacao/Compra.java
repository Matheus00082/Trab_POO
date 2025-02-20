package src.organizacao;

import java.util.HashMap;

import src.pessoas.Loja;
import src.pessoas.Pessoa;

import java.util.Map;

public class Compra {
    private String idCompra, nomeProduto;
    private Loja loja;
    private int quantidade, parcelas;
    private Double preco, precoParcelado;
    protected static Map<String, String> ids = new HashMap<>();

    public Compra(String idCompra, String nomeProduto, Loja loja, int quantidade, int parcelas, Double preco) {
        if (ids.get(idCompra) != null) {
            throw new IllegalArgumentException("ID repetido " + idCompra + " na classe Compra.");
        }

        setIdCompra(idCompra);
        this.idCompra = idCompra;
        this.nomeProduto = nomeProduto;
        this.loja = loja;
        this.loja.setLucro(preco * quantidade);
        this.quantidade = quantidade;
        this.parcelas = parcelas;
        this.preco = preco;
        this.precoParcelado = (preco * quantidade) / parcelas;
    }

    public static void verificaSeEhPessoaJuridica(Map<String, Pessoa> pessoas, String idLoja, String idCompra) {
        Pessoa pessoa = pessoas.get(idLoja);

        if (pessoa.getTipo() == "PJ") {
            throw new IllegalArgumentException("ID " + idLoja + " da Compra de id " + idCompra + " não se refere a uma Loja, mas a uma PJ.");
        }
    }
    
    public static void compraTemIdValidoDeTarefa(Map<String, Tarefa> tarefas, String idTarefa, String idCompra) {
        if (tarefas.get(idTarefa) == null) {
            throw new IllegalArgumentException("ID(s) de Tarefa " + idTarefa + " não cadastrado na " + "Compra de ID " + idCompra + ".");
        }
    }

    public static void compraTemIdValidoDeLoja(Map<String, Pessoa> lojas, String idLoja, String idCompra) {
        if (lojas.get(idLoja) == null) {
            throw new IllegalArgumentException("ID(s) de Loja " + idLoja + " não cadastrado na " + "Compra de ID " + idCompra + ".");
        }
    }

    public static void setIds(String id) {
        ids.put(id, id);
    }

    public void pagaCompra(Casal casal) {
        if (this.parcelas > 0) {
            casal.subSaldoAtual(this.precoParcelado);
            this.parcelas--;
        }
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPrecoParcelado(Double precoParcelado) {
        this.precoParcelado = precoParcelado;
    }

    public Double getPrecoParcelado() {
        return precoParcelado;
    }
}
