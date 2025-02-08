package src.pessoas;

public class Loja extends PessoaJuridica{
    public String nomeProduto;
    public Float preco; // verificar se precisa msm

    public Loja(String nome, String endereco, String telefone, String id, String CNPJ, String nomeProduto, Float preco) {
        super(nome, endereco, telefone, id, CNPJ);
        this.nomeProduto = nomeProduto;
        this.preco = preco;
    }
}
