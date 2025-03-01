package src.organizacao;

import src.pessoas.Loja;
import src.pessoas.Pessoas;

import java.util.HashMap;
import java.util.Map;

public class Compra {
    private String idCompra, nomeProduto;
    private Loja loja;
    private int qtd, parcelas, parcelasAtual;
    private Double preco, precoParcelado;
    private static Map<String, Compra> compras = new HashMap<>();

    public Compra(String idCompra, Loja loja, String nomeProduto, int qtd, Double preco, int parcelas) {

        // verifica se o id da compra é válido
        if (compras.get(idCompra) != null) {
            throw new IllegalArgumentException("ID repetido " + idCompra + " na classe Compra.");
            
        }

        this.idCompra = idCompra;
        this.loja = loja;
        this.nomeProduto = nomeProduto;
        this.qtd = qtd;
        this.preco = preco;
        this.parcelas = parcelas;
        this.parcelasAtual = parcelas;
        this.precoParcelado = (preco * qtd) / parcelas;

        // adiciona a compra ao mapa de compras
        compras.put(idCompra, this);
    }
    
    // verifica se a compra tem id válido de uma loja e não de uma pessoa jurídica
    public static void verificaSeEhPessoaJuridica(String idCompra, String idLoja) {
        Pessoas pessoa = Pessoas.getPessoaCadastrada(idLoja);

        if (pessoa.getTipo() == "PJ") {
            throw new IllegalArgumentException("ID " + idLoja + " da Compra de id " + idCompra + " não se refere a uma Loja, mas a uma PJ.");
        }
    }
    
    // verifica se a compra tem id válido de tarefa
    public static void compraTemIdValidoDeTarefa(String idCompra, String idTarefa) {
        if (Tarefa.getTarefaCadastrada(idTarefa) == null) {
            throw new IllegalArgumentException("ID(s) de Tarefa " + idTarefa + " não cadastrado na " + "Compra de ID " + idCompra + ".");
        }
    }

    // verifica se a compra tem id válido de loja
    public static void compraTemIdValidoDeLoja(String idCompra, String idLoja) {
        if (Pessoas.getPessoaCadastrada(idLoja) == null) {
           throw new IllegalArgumentException("ID(s) de Loja " + idLoja + " não cadastrado na " + "Compra de ID " + idCompra + ".");
        }
    }

    public void pagaCompra(Casal casal) {
        if (this.parcelas > 0) {
            casal.subSaldoAtual(this.precoParcelado);
            this.parcelasAtual--;
        }
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getIdCompra() {
        return this.idCompra;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public Loja getLoja() {
        return this.loja;
    }

    public void setQuantidade(int quantidade) {
        this.qtd = quantidade;
    }

    public int getQuantidade() {
        return this.qtd;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public int getParcelas() {
        return this.parcelas;
    }

    public int getParcelasAtual() {
        return this.parcelasAtual;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPreco() {
        return this.preco;
    }

    public void setPrecoParcelado(Double precoParcelado) {
        this.precoParcelado = precoParcelado;
    }

    public Double getPrecoParcelado() {
        return this.precoParcelado;
    }

};