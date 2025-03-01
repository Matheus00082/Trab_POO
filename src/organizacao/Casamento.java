package src.organizacao;

import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import src.pessoas.Pessoas;

public class Casamento {
    private String idCasamento, idConjuge1, idConjuge2;
    private String hora, local;
    private Date data;
    private Festa festa;
    private static Map<String, Casamento> casamentos = new HashMap<>();

    public Casamento(String idCasamento, String idconjuge1, String idConjuge2, String data, String hora, String local) throws ParseException {
        
        // Verifica se o ID do casamento já foi cadastrado
        if (casamentos.get(idCasamento) != null) {
            throw new IllegalArgumentException("ID repetido " + idCasamento + " na classe Casamento.");
        }
        
        this.idCasamento = idCasamento;
        this.idConjuge1 = idconjuge1;
        this.idConjuge2 = idConjuge2;

        try {
            this.data = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }

        this.hora = hora;
        this.local = local;
        this.festa = null;

        // Adiciona o casamento ao Map de casamentos
        casamentos.put(idCasamento, this);
    }

    // Verifica se o conjuge está cadastrada no casamento
    public static void pessoaEstaCadastradaCasamento(String idCasamento, String id1, String id2) {
        Map<String, Pessoas> pessoas = Pessoas.getPessoas();

        if (pessoas.get(id1) == null && pessoas.get(id2) == null) {
            throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " " + id2 + " não cadastrado no Casamento de ID " + idCasamento + ".");
        } else if (pessoas.get(id1) == null && pessoas.get(id2) != null){
            throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " não cadastrado no Casamento de ID " + idCasamento + ".");
        } else if (pessoas.get(id1) != null && pessoas.get(id2) == null){
            throw new IllegalArgumentException("ID(s) de Pessoa " + id2 + " não cadastrado no Casamento de ID " + idCasamento + ".");
        }
    }

    // Verifica se é o mês de pagamento da festa de casamento
    public boolean ehMesdePagamentoCasamento(Date data) throws ParseException {
        if (this.festa.ehMesPagamentoFesta(data)) {
            return true;
        }

        return false;
    }

    // Se for o mês de pagamento, paga a festa de casamento
    public void pagaFestaCasamento(Date data, Casal casal) throws ParseException {
        if (this.festa != null) {
            if (ehMesdePagamentoCasamento(data)) {
                this.festa.setParcelasAtual(this.festa.getParcelasAtual() - 1);
                casal.subSaldoAtual(this.festa.getPrecoParcelado());
            }
        }
    }

    public static Map<String, Casamento> getCasamentos() {
        return casamentos;
    }

    public static Casamento getCasamentoCadastrado(String idCasamento) {
        return casamentos.get(idCasamento);
    }

    public void setIdCasamento(String idCasamento) {
        this.idCasamento = idCasamento;
    }

    public String getIdCasamento() {
        return this.idCasamento;
    }

    public void setIdConjuje1(String idConjuge1) {
        this.idConjuge1 = idConjuge1;
    }

    public String getIdConjuje1() {
        return this.idConjuge1;
    }

    public void setIdConjuje2(String idConjuge2) {
        this.idConjuge2 = idConjuge2;
    }

    public String getIdConjuje2() {
        return this.idConjuge2;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return this.hora;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocal() {
        return this.local;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return this.data;
    }

    public void setFesta(Festa festa) {
        this.festa = festa;
    }

    public Festa getFesta() {
        return this.festa;
    }
}