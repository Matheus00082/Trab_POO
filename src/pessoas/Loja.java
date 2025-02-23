package src.pessoas;

public class Loja extends PessoaJuridica {

    public Loja(String id, String nome, String telefone, String endereco, String CNPJ) {
        super(id, nome, telefone, endereco, CNPJ);
        this.tipo = "Loja";
        this.lucro = 0.0;
        this.prestador = true;
    }
    
}