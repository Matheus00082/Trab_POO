package src.pessoas;

public class Loja extends PessoaJuridica{
    public String nomeProduto;
    public Double preco; // verificar se precisa msm

    public Loja(String nome, String endereco, String telefone, String id, String CNPJ) {
        super(nome, endereco, telefone, id, CNPJ);
    }
}
