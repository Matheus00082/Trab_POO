package src.organizacao;

import java.util.List;

import src.pessoas.PessoaFisica;

public class Casamento {
    public String idCasamento;
    public Casal casal;
    public String data, hora, local;
    public Festa festa;

    public Casamento(String idCasamento, PessoaFisica conjuge1, PessoaFisica conjuge2, String data, String hora, String local) {
        this.idCasamento = idCasamento;
        this.casal = new Casal(conjuge1, conjuge2);
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.festa = null;
    }

    public static int calculaNumCasamentos(List<Casamento> casamentos, Casal casal) {
        int num = 0;

        for (Casamento casamento : casamentos) {
            if (casamento.casal != casal && casamento.festa.casalFoiConvidado(casal)) {
                num++;
            }
        }
        
        return num;
    }
}
