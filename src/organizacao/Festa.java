package src.organizacao;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import src.auxiliares.GerenciadorDeDatas;

public class Festa {
    private String idFesta, local, hora;
    private Date data;
    private String[] convidados;
    private Double preco, precoParcelado;
    private int parcelasInicial;
    private int parcelasAtual;
    private static Map<String, Festa> festas = new HashMap<>();

    public Festa(String idFesta, String local, String data, String hora, Double preco, int parcelas,
            String[] convidados) throws ParseException {

        // Verifica se o ID da festa já foi cadastrado
        if (festas.get(idFesta) != null) {
            throw new IllegalArgumentException("ID repetido " + idFesta + " na classe Festa.");
        }

        this.idFesta = idFesta;
        this.local = local;

        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            this.data = formato.parse(data.replace(" ", ""));
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }

        this.hora = hora;
        this.preco = preco;
        this.parcelasInicial = parcelas;
        this.parcelasAtual = parcelas;
        this.convidados = convidados;
        this.precoParcelado = preco / parcelas;

        // Adiciona a festa ao mapa de festas
        festas.put(idFesta, this);
    }

    /*Verifica se a data passada é igual ou está entre a data de 
    *inicio do pagamento festa e a data final de pagamento 
    */
    public boolean ehMesPagamentoFesta(Date data) throws ParseException{
        if (GerenciadorDeDatas.ehMesmoMeseAno(data, this.data) || ((this.parcelasAtual < this.parcelasInicial) && (this.parcelasAtual > 0))) {
            return true;
        }
        return false;
    }

    public static void casamentoEstaCadastrado(String idFesta, String idCasamento) {
        Map<String, Casamento> casamentos = Casamento.getCasamentos();

        if (casamentos.get(idCasamento) == null) {
            throw new IllegalArgumentException( "ID(s) de Casamento " + idCasamento + " não cadastrado na Festa de ID " + idFesta + ".");
        }
    }

    // Verifica se o casal foi convidado para a festa
    public boolean casalFoiConvidado(Casal casal) {
        int cont = 0;
        String nome1 = casal.getConjuge1().getNome().replace(" ", "");
        String nome2 = casal.getConjuge2().getNome().replace(" ", "");

        for (String convidado : this.convidados) {

            if (nome1.equals(convidado) || nome2.equals(convidado)) {
                cont++;
            }

            // Se ambos foram convidados
            if (cont == 2)
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
            throw new ParseException("Erro de formatação", 0);
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
}