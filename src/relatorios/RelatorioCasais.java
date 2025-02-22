package src.relatorios;

import src.organizacao.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RelatorioCasais {

    public static void geraRelatorioCasais(String caminho, Map<String, Casamento> casamentos, List<Casal> casais) throws IOException {
        for (Casal casal : casais) {

            casal.setNumCasamentos(Casamento.calculaNumCasamentos(casamentos, casal));

            if (casal.getCasamento() != null && casal.getCasamento().getFesta() != null) {
                casal.setTotalGasto(casal.getCasamento().getFesta().getPreco());
            }

            Lar lar = casal.getLar();

            if (lar != null) {
                Double gastos = 0.0;
                for (Tarefa tarefa : lar.getTarefas().values()) {
                    gastos += tarefa.calculaValorTotal();
                }

                casal.setTotalGasto(casal.getTotalGasto() + gastos);
            }
        }

        casais.sort((Casal c1, Casal c2) -> {
            if (c1.getTotalGasto() == c2.getTotalGasto()) {
                if (c1.getConjuge1().getNome().replace(" ", "").equals(c2.getConjuge1().getNome().replace(" ", ""))) {
                    return c1.getNumCasamentos() - c2.getNumCasamentos();
                }
                return c1.getConjuge1().getNome().replace(" ", "").compareTo(c2.getConjuge1().getNome().replace(" ", ""));
            }
            return Double.compare(c2.getTotalGasto(), c1.getTotalGasto());
        });

        String arquivo = "/saida/3-estatisticas-casais.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho+arquivo,true))) {

            for (Casal casal : casais) {
                String totalGasto = String.format("R$ %.2f", casal.getTotalGasto()).replace('.', ',');
                String nome1 = casal.getConjuge1().getNome().trim().replaceAll("\\s+", " ");
                String nome2 = casal.getConjuge2().getNome().trim().replaceAll("\\s+", " ");
                writer.write(nome1 + ";" + nome2 + ";"  + totalGasto + ";" + casal.getNumCasamentos());
                writer.newLine();
            }

        } catch (IOException e) {
            throw new IOException("Erro de I/O");
        }
    }

}
