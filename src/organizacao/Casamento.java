package src.organizacao;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Casamento {
    private String idCasamento, idConjuje1, idConjuje2;
    private String hora, local;
    private Date data;
    private Festa festa;

    public Casamento(String idCasamento, String idConjuje1, String idConjuje2, String data, String hora, String local) {
        this.idCasamento = idCasamento;

        try {
            this.data = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.hora = hora;
        this.local = local;
        this.festa = null;
        this.idConjuje1 = idConjuje1;
        this.idConjuje2 = idConjuje2;
    }

    public static int calculaNumCasamentos(List<Casamento> casamentos, Casal casal) {
        int num = 0;

        // Calcula numero de casamentos que ambos foram
        for (Casamento casamento : casamentos) {
            // Se o casal não é o casal do casamento
            String idConjuge1 = casal.getConjuge1().getId();
            if (!idConjuge1.equals(casamento.idConjuje1) && !idConjuge1.equals(casamento.idConjuje2)) {
                if (casamento.festa != null && casamento.festa.casalFoiConvidado(casal)) {
                    num++;
                }
            }
        }

        return num;
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
