package src.pessoas;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class PessoaFisica extends Pessoas {
    private String cpf;
    private Date datadeNascimento;
    private Double dinheiroGuardado, salario, gastosMensais;

    public PessoaFisica(String id, String nome, String telefone, String endereco, String cpf, String datadeNascimento,
            Double dinheiroGuardado, Double salario, Double gastoMensal) throws Exception {
        super(id, "PF", nome, telefone, endereco);

        // Verifica se o CPF é repetido
        for (Pessoas p : Pessoas.getPessoas().values()) {
            if (p.getTipo().equals("PF")) {
                PessoaFisica pf = (PessoaFisica) p;
                String CPF = pf.getCPF();
                if (CPF != null && CPF.equals(cpf)) {
                    throw new IllegalArgumentException("O CPF " + cpf + " da Pessoa " + id + " é repetido.");
                }
            }
        }

        this.cpf = cpf;
        this.dinheiroGuardado = dinheiroGuardado;
        this.salario = salario;
        this.gastosMensais = gastoMensal;
        this.prestador = false;
        this.lucro = 0.0;

        try {
            this.datadeNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(datadeNascimento);
        } catch (Exception e) {
           throw new ParseException("Erro de formatação", 0);
        }

    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCPF() {
        return this.cpf;
    }

    public void setDatadeNascimento(String datadeNascimento) throws ParseException {
        try {
            this.datadeNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(datadeNascimento);
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }
    }

    public Date getDatadeNascimento() {
        return this.datadeNascimento;
    }

    public void setDinheiroGuardado(Double dinheiroGuardado) {
        this.dinheiroGuardado = dinheiroGuardado;
    }

    public Double getDinheiroGuardado() {
        return this.dinheiroGuardado;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getSalario() {
        return this.salario;
    }

    public void setGastoMensal(Double gastoMensal) {
        this.gastosMensais = gastoMensal;
    }

    public Double getGastoMensal() {
        return this.gastosMensais;
    }
}