package src.relatorios;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import src.organizacao.Casal;
import src.organizacao.Casamento;

public class RelatorioCasais {

    private static void imprimeRelatorio(String caminho, List<Casal> casais) throws IOException{
        String arquivo = "/saida/3-estatisticas-casais.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho+arquivo,true))) {

            for (Casal casal : casais) {
                String totalGasto = String.format("R$ %.2f", casal.getTotalGasto()).replace('.', ',');
                writer.write(casal.getConjuge1().getNome() + ";" 
                            + casal.getConjuge2().getNome() + ";" 
                            + totalGasto + ";" 
                            + casal.getNumCasamentos());
                writer.newLine();
            }

        } catch (IOException e) {
            throw new IOException("Erro de I/O");
        }
    }

    private static void ordenaCasais(List<Casal> casais) {
        casais.sort((Casal c1, Casal c2) -> {
            if (c1.getTotalGasto().equals(c2.getTotalGasto())) {
                return c1.getConjuge1().getNome().compareTo(c2.getConjuge1().getNome());
            }
            return (int)(c2.getTotalGasto() - c1.getTotalGasto());
        });
    }

    public static void geraRelatorioCasais(String caminho) throws IOException{
        Map<String, Casamento> casamentos = Casamento.getCasamentos();
        List<Casal> casais = new ArrayList<>(Casal.getCasais().values());

        for (Casal casal : casais) {
            casal.calculaNumCasamentos(casamentos);
            casal.calculaTotalGasto();
        }

        ordenaCasais(casais);
        imprimeRelatorio(caminho, casais);
    }
}