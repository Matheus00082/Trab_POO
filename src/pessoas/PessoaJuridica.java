package src.pessoas;

import java.util.HashMap;
import java.util.Map;

public class PessoaJuridica extends Pessoa{
    protected String CNPJ;
    protected static Map<String, String> cnpjs = new HashMap<>();


    public PessoaJuridica(String nome, String endereco, String telefone, String id, String CNPJ) {
        super(nome, endereco, telefone, id, "PJ");

        if (cnpjs.containsKey(CNPJ) && !cnpjs.get(CNPJ).equals(id)) {
            throw new IllegalArgumentException("O CNPJ " + CNPJ + " da Pessoa " + id + " é repetido.");
        }

        cnpjs.put(CNPJ, id);

        this.CNPJ = CNPJ;
    }

    public void setCnpj(String cnpj) {
        this.CNPJ = cnpj;
    }

    public String getCNPJ() {
        return CNPJ;
    }
}
