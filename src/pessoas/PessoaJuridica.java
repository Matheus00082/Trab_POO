package src.pessoas;

public class PessoaJuridica extends Pessoa{
    public String CNPJ;

    public PessoaJuridica(String nome, String endereco, String telefone, String id, String CNPJ) {
        super(nome, endereco, telefone, id, 'J');
        this.CNPJ = CNPJ;
    }
}
