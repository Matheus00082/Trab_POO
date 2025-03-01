import src.auxiliares.*;
import src.organizacao.*;
import src.pessoas.*;
import src.relatorios.*;

public class Main {
    public static void main(String[] args) {

        String caminho = args[0].replaceAll(" ", "");

        try {
            // Cria arquivos vazios a serem preenchidos posteriormente
            LeitorCVS.criaArquivosVazios(caminho);
            // Lê os arquivos CSV e preenche o "banco de dados" 
            LeitorCVS.lerPessoas(caminho);
            LeitorCVS.lerLares(caminho);
            LeitorCVS.lerTarefas(caminho);
            LeitorCVS.lerCasamentos(caminho);
            LeitorCVS.lerFestas(caminho);
            LeitorCVS.lerCompras(caminho);
            // Gera relatórios
            RelatorioPlanejamento.geraRelatorioPlanejamento(caminho);
            if (Pessoas.getPessoas() != null) {
                RelatorioPrestadores.geraRelatorioPrestadores(caminho);
            } if (Casamento.getCasamentos() != null && Casal.getCasais() != null) {
                RelatorioCasais.geraRelatorioCasais(caminho);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());  
            System.exit(0);
        }
    }
}
