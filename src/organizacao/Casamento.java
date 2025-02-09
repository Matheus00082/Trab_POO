package src.organizacao;

import java.util.List;

public class Casamento {
    public String idCasamento, idConjuje1, idConjuje2;
    public String data, hora, local;
    public Festa festa;

    public Casamento(String idCasamento, String idConjuje1, String idConjuje2, String data, String hora, String local) {
        this.idCasamento = idCasamento;
        this.data = data;
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
            if (!casal.conjuge1.id.equals(casamento.idConjuje1) && !casal.conjuge1.id.equals(casamento.idConjuje2)) {
                if (casamento.festa.casalFoiConvidado(casal)) {
                    num++;
                }
            }
        }

        return num;
    }
}
