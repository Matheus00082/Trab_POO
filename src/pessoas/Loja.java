package src.pessoas;

public class Loja extends PessoaJuridica{

    public Loja(String nome, String endereco, String telefone, String id, String CNPJ) {
        super(nome, endereco, telefone, id, CNPJ);
        this.tipo = "Loja";
        this.lucro = 0.0;
    }
}
