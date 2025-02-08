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

}
