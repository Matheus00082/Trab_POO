package src.auxiliares;

import src.pessoas.Loja;

public class Compra {
    public String idCompra, idTarefa, nomeProduto;
    public Loja loja;
    public int quantidade, parcelas;
    public Float preco;

    public Compra(String idCompra, String idTarefa, String nomeProduto, Loja loja, int quantidade, int parcelas, Float preco) {
        this.idCompra = idCompra;
        this.idTarefa = idTarefa;
        this.nomeProduto = nomeProduto;
        this.loja = loja;
        this.quantidade = quantidade;
        this.parcelas = parcelas;
        this.preco = preco;
    }

}
