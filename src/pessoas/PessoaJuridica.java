package src.pessoas;


public class PessoaJuridica extends Pessoas {
    protected String cnpj;

    public PessoaJuridica(String id, String nome, String telefone, String endereco, String CNPJ) {
        super(id, "PJ", nome, telefone, endereco);
        
        // verifica se o CNPJ é repetido
        for (Pessoas p : Pessoas.getPessoas().values()) {
            if (p.getTipo().equals("PJ")) {
                PessoaJuridica pj = (PessoaJuridica) p;
                String cnpj = pj.getCNPJ();
                if (cnpj != null && cnpj.equals(CNPJ)) {
                    throw new IllegalArgumentException("O CNPJ " + CNPJ + " da Pessoa " + id + " é repetido.");
                }
            }
        }

        this.cnpj = CNPJ;
        this.lucro = 0.0;
        this.prestador = true;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCNPJ() {
        return this.cnpj;
    }

}