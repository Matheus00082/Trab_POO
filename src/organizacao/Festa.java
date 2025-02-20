package src.organizacao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.text.ParseException;
import src.auxiliares.DataFunctions;

import java.text.SimpleDateFormat;

public class Festa {
    private String idFesta, local, hora;
    private Date data;
    private String[] convidados;
    private Double preco, precoParcelado;
    private int parcelasInicial;
    private int parcelasAtual;
    protected static Map<String, String> ids = new HashMap<>();

    public Festa(String idFesta, String local, String data, String hora,
            Double preco, int parcelas, String[] convidados) throws ParseException {
        this.idFesta = idFesta;
        this.local = local;
        
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            this.data = formato.parse(data);
        } catch (Exception e) {
            throw new ParseException("Erro ao formatar a data", 0);
        }

        this.hora = hora;
        this.convidados = convidados;
        this.preco = preco;
        this.parcelasInicial = parcelas;
        this.parcelasAtual = parcelas;
        this.precoParcelado = preco/parcelasInicial;

        if (ids.get(idFesta) != null) {
            throw new IllegalArgumentException("ID repetido " + idFesta + " na classe Festa.");
        }
    }

    public static void casamentoEstaCadastrado(Map<String, Casamento> casamentos, String idCasamento, String idFesta) {
        if (casamentos.get(idCasamento) == null) {
            throw new IllegalArgumentException("ID(s) de Casamento " + idCasamento + " não cadastrado na Festa de ID " + idFesta + ".");
        }
    }

    public static void setIds(String id) {
        ids.put(id, id);
    }

    public boolean ehMesDePagamentoFesta(Date data) throws ParseException {
        if ((DataFunctions.ehMesmoMeseAno(data, this.data) || (parcelasAtual != parcelasInicial)) && (parcelasAtual != 0)) {
            return true;
        }
        return false;
    }

    public void setIdFesta(String idFesta) {
        this.idFesta = idFesta;
    }

    public String getIdFesta() {
        return this.idFesta;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocal() {
        return this.local;
    }

    public void setData(String data) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.data = formato.parse(data);
        } catch (Exception e) {
           throw new ParseException("Erro ao formatar a data", 0);
        }
    }

    public Date getData() {
        return this.data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return this.hora;
    }

    public void setConvidados(String[] convidados) {
        this.convidados = convidados;
    }

    public String[] getConvidados() {
        return this.convidados;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPreco() {
        return this.preco;
    }

    public void setPrecoParcelado(Double precoParcelado) {
        this.precoParcelado = precoParcelado;
    }

    public Double getPrecoParcelado() {
        return this.precoParcelado;
    }

    public void setParcelasAtual(int parcelas) {
        this.parcelasAtual = parcelas;
    }

    public int getParcelasInicial() {
        return this.parcelasInicial;
    }

    public int getParcelasAtual() {
        return this.parcelasAtual;
    }

    public boolean casalFoiConvidado(Casal casal) {
        int cont = 0;

        for (String convidado : this.convidados) {
            String nome1 = casal.getConjuge1().getNome().replaceAll(" ", "");
            String nome2 = casal.getConjuge2().getNome().replaceAll(" ", "");
            String nomeConvidado = convidado.replaceAll(" ", "");

            if (nome1.equals(nomeConvidado) || nome2.equals(nomeConvidado)) {
                cont++;
            }

            if (cont == 2)
                return true;
        }

        return false;
    }

}
