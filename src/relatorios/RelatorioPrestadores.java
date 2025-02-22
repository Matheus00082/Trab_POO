package src.relatorios;

import src.pessoas.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RelatorioPrestadores {

    public static void geraRelatorioPrestadores(String caminho, Map<String, Pessoa> prestadores) throws IOException {

        List<Pessoa> prestadoresList = new ArrayList<>(prestadores.values());

        prestadoresList.sort((p1, p2) -> {
            if(p1.getTipo().equals(p2.getTipo())) {
                if (p2.getLucro() == p1.getLucro()) {
                    return p1.getNome().replaceAll(" ", "").compareTo(p2.getNome().replaceAll(" ", ""));
                }
                return Double.compare(p2.getLucro(), p1.getLucro());
            }
            return Pessoa.compararTipos(p1.getTipo(), p2.getTipo());
        });

        String arquivo = "/saida/2-estatisticas-prestadores.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho+arquivo, true))) {

            for (Pessoa prestador : prestadoresList) {
                if (prestador.getEhPrestador()) {
                    String lucro = String.format("R$ %.2f", prestador.getLucro()).replace('.', ',');
                    writer.write(prestador.getTipo() + ";" + prestador.getNome() + ";" + lucro);
                    writer.newLine();
                }
            }
            
        } catch (Exception e) {
        throw new IOException("Erro de I/O");

        }
    }
}
