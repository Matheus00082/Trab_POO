package src.relatorios;

import src.organizacao.Tarefa;
import src.pessoas.Pessoas;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RelatorioPrestadores {

    private static void imprimeRelatorio(String caminho, List<Pessoas> prestadores) throws IOException {
        String arquivo = caminho + "/saida/2-estatisticas-prestadores.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {

            for (Pessoas prestador : prestadores) {
                String lucro = String.format("R$ %.2f", prestador.getLucro()).replace('.', ',');
                writer.write(prestador.getTipo() + ";" + prestador.getNome() + ";" + lucro);
                writer.newLine();
            }

        } catch (Exception e) {
            throw new IOException("Erro de I/O");
        }
    }

    // ordena a lista de prestadores de acordo com o tipo, lucro e nome
    private static void ordenaPrestadores(List<Pessoas> prestadores) {
        prestadores.sort((p1, p2) -> {
            if (p1.getTipo().equals(p2.getTipo())) {
                if (p1.getLucro().equals(p2.getLucro())) {
                    return p1.getNome().compareTo(p2.getNome());
                }
                return (int) (p2.getLucro() - p1.getLucro());
            }
            return Pessoas.compararTipos(p1.getTipo(), p2.getTipo());
        });
    }

    private static void criaListaPrestadores(List<Pessoas> prestadores) {
        // verifica quais pessoas são prestadores e adiciona na lista
        Map<String, Pessoas> pessoas = Pessoas.getPessoas();
        for (Pessoas pessoa : pessoas.values()) {
            if (pessoa.getEhPrestador()) {
                prestadores.add(pessoa);
            }
        }
    }

    public static void geraRelatorioPrestadores(String caminho) throws IOException {
        Map<String, Tarefa> tarefas = Tarefa.getTarefas();
        // calcula o lucro de cada prestador
        for (Tarefa tarefa : tarefas.values()) {
            tarefa.calculaLucroPrestadores();
        }

        // cria lista de prestadores, ordena e imprime o relatório
        List<Pessoas> prestadores = new ArrayList<>();
        criaListaPrestadores(prestadores);
        ordenaPrestadores(prestadores);
        imprimeRelatorio(caminho, prestadores);
    }
}