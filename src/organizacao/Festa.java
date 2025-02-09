package src.organizacao;

public class Festa {
    public String idFesta, local, data, hora;
    public String[] convidados;
    public Double preco;
    public int parcelas;

    public Festa(String idFesta, String local, String data, String hora,
            Double preco, int parcelas, String[] convidados) {
        this.idFesta = idFesta;
        this.local = local;
        this.data = data;
        this.hora = hora;
        this.convidados = convidados;
        this.preco = preco;
        this.parcelas = parcelas;
    }

    public boolean casalFoiConvidado(Casal casal) {
        int cont = 0;

        for (String convidado : this.convidados) {
            if (casal.conjuge1.nome == convidado || casal.conjuge2.nome == convidado) {
                cont++;
            }

            if (cont == 2)
                return true;
        }
        
        return false;
    }

}
