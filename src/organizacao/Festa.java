package src.organizacao;

import java.util.Date;

import src.auxiliares.DataFunctions;

import java.text.SimpleDateFormat;

public class Festa {
    private String idFesta, local, hora;
    private Date data;
    private String[] convidados;
    private Double preco, precoParcelado;
    private int parcelasInicial;
    private int parcelasAtual;

    public Festa(String idFesta, String local, String data, String hora,
            Double preco, int parcelas, String[] convidados) {
        this.idFesta = idFesta;
        this.local = local;
        
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            this.data = formato.parse(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.hora = hora;
        this.convidados = convidados;
        this.preco = preco;
        this.parcelasInicial = parcelas;
        this.parcelasAtual = parcelas;
        this.precoParcelado = preco/parcelasInicial;
    }

    public boolean ehMesDePagamentoFesta(Date data) {
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

    public void setData(String data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.data = formato.parse(data);
        } catch (Exception e) {
            e.printStackTrace();
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
            if (casal.getConjuge1().getNome() == convidado || casal.getConjuge2().getNome() == convidado) {
                cont++;
            }

            if (cont == 2)
                return true;
        }

        return false;
    }

}
