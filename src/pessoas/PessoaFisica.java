package src.pessoas;

public class PessoaFisica extends Pessoa{
    public String CPF, datadeNascimento;
    public Double poupanca, salario, gastoMensal, juros;

    public PessoaFisica(String nome, String endereco, String telefone, String id, String CPF, String datadeNascimento, Double poupanca, Double salario, Double gastoMensal) {
        super(nome, endereco, telefone, id, "PF");
        this.CPF = CPF;
        this.datadeNascimento = datadeNascimento;
        this.poupanca = poupanca;
        this.salario = salario;
        this.gastoMensal = gastoMensal;
        this.juros = 0.05;
    }

    public static boolean ehIgualCpf(PessoaFisica p, String cpf) {
        if (p.CPF == cpf) return true;
        else return false;
    }
    
}
