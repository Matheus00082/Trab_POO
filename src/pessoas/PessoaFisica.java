package src.pessoas;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import java.text.ParseException;

import java.util.HashMap;

public class PessoaFisica extends Pessoa{
    private String CPF;
    private Date datadeNascimento;
    private Double poupanca, salario, gastoMensal, juros;
    protected static Map<String, String> cpfs = new HashMap<>();

    public PessoaFisica(String nome, String endereco, String telefone, String id, String CPF, String datadeNascimento, Double poupanca, Double salario, Double gastoMensal) throws Exception {
        super(nome, endereco, telefone, id, "PF");

        if (cpfs.containsKey(CPF) && !cpfs.get(CPF).equals(id)) {
            throw new IllegalArgumentException("O CPF " + CPF + " da Pessoa " + id + " é repetido.");
        }

        cpfs.put(CPF, id);
        
        this.CPF = CPF;

        try {
            this.datadeNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(datadeNascimento);
        } catch (Exception e) {
            throw new ParseException("Erro ao formatar a data", 0);
        }

        this.poupanca = poupanca;
        this.salario = salario;
        this.gastoMensal = gastoMensal;
        this.juros = 0.05;
    }

    public static boolean ehIgualCpf(PessoaFisica p, String cpf) {
        if (p.CPF == cpf) return true;
        else return false;
    }

    public void setCpf(String cpf) {
        this.CPF = cpf;
    }

    public String getCPF() {
        return CPF;
    }

    public void setDatadeNascimento(String datadeNascimento) throws ParseException {
        try {
            this.datadeNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(datadeNascimento);
        } catch (Exception e) {
            throw new ParseException("Erro ao formatar a data", 0);
        }
    }

    public Date getDatadeNascimento() {
        return datadeNascimento;
    }

    public void setPoupanca(Double poupanca) {
        this.poupanca = poupanca;
    }

    public Double getPoupanca() {
        return poupanca;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setGastoMensal(Double gastoMensal) {
        this.gastoMensal = gastoMensal;
    }

    public Double getGastoMensal() {
        return gastoMensal;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public Double getJuros() {
        return juros;
    }   
}
