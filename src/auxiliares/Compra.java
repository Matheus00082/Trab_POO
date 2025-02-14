package src.auxiliares;

import src.organizacao.Casal;
import src.pessoas.Loja;

public class Compra {
    private String idCompra, nomeProduto;
    private Loja loja;
    private int quantidade, parcelas;
    private Double preco, precoParcelado;

    public Compra(String idCompra, String nomeProduto, Loja loja, int quantidade, int parcelas, Double preco) {
        this.idCompra = idCompra;
        this.nomeProduto = nomeProduto;
        this.loja = loja;
        this.loja.setLucro(preco * quantidade);
        this.quantidade = quantidade;
        this.parcelas = parcelas;
        this.preco = preco;
        this.precoParcelado = (preco * quantidade)/parcelas;
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
