package src.organizacao;

public class Festa {
    public String idFesta, local, data, hora;
    public String[] convidados;
    public Casamento casorio;
    public Double preco;
    public int parcelas;

    public Festa(String idFesta, String local, String data, String hora, String[] convidados, Casamento casorio, Double preco, int parcelas) {
        this.idFesta = idFesta;
        this.local = local;
        this.data = data;
        this.hora = hora;
        this.convidados = convidados;
        this.casorio = casorio;
        this.preco = preco;
        this.parcelas = parcelas;
    }

}
