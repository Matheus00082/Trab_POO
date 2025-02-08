package src.auxiliares;

import src.pessoas.Loja;

public class Compra {
    public String idCompra, nomeProduto;
    public Loja loja;
    public int quantidade, parcelas;
    public Double preco;

    public Compra(String idCompra, String nomeProduto, Loja loja, int quantidade, int parcelas, Double preco) {
        this.idCompra = idCompra;
        this.nomeProduto = nomeProduto;
        this.loja = loja;
        this.quantidade = quantidade;
        this.parcelas = parcelas;
        this.preco = preco;
    }

}
