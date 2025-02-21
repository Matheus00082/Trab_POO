package src.organizacao;

import java.util.Map;

import java.text.ParseException;
import src.pessoas.Pessoa;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class Casamento {
    private String idCasamento, idConjuje1, idConjuje2;
    private String hora, local;
    private Date data;
    private Festa festa;
    protected static Map<String, String> ids = new HashMap<>();

    public static void pessoaEstaCadastradaCasamento(Map<String, Pessoa> pessoas, String id1, String id2, String idCasamento) {
        if (pessoas.get(id1) == null && pessoas.get(id2) == null) {
            throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " " + id2 + " não cadastrado no Casamento de ID " + idCasamento + ".");
        } else if (pessoas.get(id1) == null && pessoas.get(id2) != null){
            throw new IllegalArgumentException("ID(s) de Pessoa " + id1 + " não cadastrado no Casamento de ID " + idCasamento + ".");
        } else if (pessoas.get(id1) != null && pessoas.get(id2) == null){
            throw new IllegalArgumentException("ID(s) de Pessoa " + id2 + " não cadastrado no Casamento de ID " + idCasamento + ".");
        }
    }

    public Casamento(String idCasamento, String idConjuje1, String idConjuje2, String data, String hora, String local) throws Exception{
        this.idCasamento = idCasamento;

        try {
            this.data = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }

        this.hora = hora;
        this.local = local;
        this.festa = null;
        this.idConjuje1 = idConjuje1;
        this.idConjuje2 = idConjuje2;

        if (ids.get(idCasamento) != null) {
            throw new IllegalArgumentException("ID repetido " + idCasamento + " na classe Casamento.");
        }
    }

    public static void setIds(String id) {
        ids.put(id, id);
    }

    public static int calculaNumCasamentos(Map<String, Casamento> casamentos, Casal casal) {
        int num = 0;

        for (Casamento casamento : casamentos.values()) {
            String idConjuge1 = casal.getConjuge1().getId();
            if (!idConjuge1.equals(casamento.idConjuje1) && !idConjuge1.equals(casamento.idConjuje2)) {
                if (casamento.festa != null && casamento.festa.casalFoiConvidado(casal)) {
                    num++;
                }
            }
        }

        return num;
    }

    public boolean ehMesdePagamentoCasamento(Date data) throws ParseException {
        if (this.festa.ehMesDePagamentoFesta(data)) {
            return true;
        }

        return false;
    }

    public void pagaFestaCasamento(Date data, Casal casal) throws ParseException {
        if (this.festa != null) {
            if (ehMesdePagamentoCasamento(data)) {
                this.festa.setParcelasAtual(this.festa.getParcelasAtual() - 1);
                casal.subSaldoAtual(this.festa.getPrecoParcelado());
            }
        }
    }

    public void setIdCasamento(String idCasamento) {
        this.idCasamento = idCasamento;
    }

    public String getIdCasamento() {
        return this.idCasamento;
    }

    public void setIdConjuje1(String idConjuje1) {
        this.idConjuje1 = idConjuje1;
    }

    public String getIdConjuje1() {
        return this.idConjuje1;
    }

    public void setIdConjuje2(String idConjuje2) {
        this.idConjuje2 = idConjuje2;
    }

    public String getIdConjuje2() {
        return this.idConjuje2;
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
