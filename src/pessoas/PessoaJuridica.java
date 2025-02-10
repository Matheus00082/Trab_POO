package src.pessoas;

public class PessoaJuridica extends Pessoa{
    protected String CNPJ;

    public PessoaJuridica(String nome, String endereco, String telefone, String id, String CNPJ) {
        super(nome, endereco, telefone, id, "PJ");
        this.CNPJ = CNPJ;
    }

    public void setCnpj(String cnpj) {
        this.CNPJ = cnpj;
    }

    public String getCNPJ() {
        return CNPJ;
    }
}
