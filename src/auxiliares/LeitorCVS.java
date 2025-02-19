package src.auxiliares;

import src.pessoas.*;
import src.organizacao.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;

public class LeitorCVS {

    public static void criaArquivosVazios() {
        try {
            new PrintWriter("1-planejamento.csv").close();
            new PrintWriter("2-estatisticas-prestadores.csv").close();
            new PrintWriter("3-estatisticas-casais.csv").close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Pessoa> lerPessoas(String caminhoArquivo) throws IOException {
        Map<String, Pessoa> pessoas = new HashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");
                String tipo = partes[1];

                if (tipo.equals("F")) {

                    pessoas.put(partes[0],
                            new PessoaFisica(partes[2], partes[4], partes[3], partes[0], partes[5], partes[6],
                                    Double.parseDouble(partes[7].replace(",", ".")),
                                    Double.parseDouble(partes[8].replace(",", ".")),
                                    Double.parseDouble(partes[9].replace(",", "."))));

                } else if (tipo.equals("J")) {

                    pessoas.put(partes[0], new PessoaJuridica(partes[2], partes[4], partes[3], partes[0], partes[5]));

                } else if (tipo.equals("L")) {

                    pessoas.put(partes[0], new Loja(partes[2], partes[4], partes[3], partes[0], partes[5]));

                }

                Pessoa.setIds(partes[0]);
            }
        }
        return pessoas;
    }

    public static Map<String, Lar> lerLares(String caminhoArquivo, Map<String, Pessoa> pessoas, List<Casal> casais)
            throws IOException {
        Map<String, Lar> lares = new HashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                PessoaFisica conjuge1 = (PessoaFisica) pessoas.get(partes[1]);
                PessoaFisica conjuge2 = (PessoaFisica) pessoas.get(partes[2]);

                // Verifica se o casal foi encontrado
                if (conjuge1 == null || conjuge2 == null) {
                    throw new IllegalArgumentException("Casal não encontrado");
                }

                Casal casal = new Casal(conjuge1, conjuge2);
                Lar lar = new Lar(partes[0], partes[3], Integer.parseInt(partes[4]), partes[5]);
                casal.setLar(lar);
                casais.add(casal);
                lares.put(partes[0], lar);

                Lar.setIds(partes[0]);
            }
        }
        return lares;
    }

    public static Map<String, Tarefa> lerTarefas(String caminhoArquivo, Map<String, Pessoa> pessoas,
            Map<String, Lar> lares)
            throws IOException {
        Map<String, Tarefa> tarefas = new HashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                Pessoa trabalhador = (Pessoa) pessoas.get(partes[2]);

                if (trabalhador == null) {
                    throw new IllegalArgumentException("Trabalhador não encontrado para a tarefa");
                }

                Lar lar = lares.get(partes[1]);

                if (lar == null) {
                    throw new IllegalArgumentException("Lar não encontrado para a tarefa");
                }

                Tarefa t = new Tarefa(partes[0], partes[3], Integer.parseInt(partes[4]), Integer.parseInt(partes[6]),
                        Double.parseDouble((partes[5].replace(".", "")).replace(",", ".")), trabalhador);

                lar.getTarefas().put(partes[0], t);
                tarefas.put(partes[0], t);
                Tarefa.setIds(partes[0]);
            }
        }

        return tarefas;
    }

    public static Map<String, Casamento> lerCasamentos(String caminhoArquivo, Map<String, Pessoa> pessoas, List<Casal> casais) throws IOException {
        Map<String, Casamento> casamentos = new HashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                Casamento casamento = new Casamento(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]);

                String idConjuge1 = partes[1];
                String idConjuge2 = partes[2];

                boolean casalEncontrado = false;

                for (Casal casal : casais) {
                    String casalConjuge1 = casal.getConjuge1().getId();
                    String casalConjuge2 = casal.getConjuge2().getId();

                    if ((idConjuge1.equals(casalConjuge1) && idConjuge2.equals(casalConjuge2)) ||
                        (idConjuge1.equals(casalConjuge2) && idConjuge2.equals(casalConjuge1))) {
                        
                        casal.setCasamento(casamento);
                        casamentos.put(partes[0], casamento);
                        casalEncontrado = true;
                        break; 
                    }
                }

                if (!casalEncontrado) {
                    PessoaFisica conjuge1 = (PessoaFisica) pessoas.get(idConjuge1);
                    PessoaFisica conjuge2 = (PessoaFisica) pessoas.get(idConjuge2);

                    if (conjuge1 == null || conjuge2 == null) {
                        throw new IllegalArgumentException("Casal não encontrado");
                    }

                    Casal novoCasal = new Casal(conjuge1, conjuge2);
                    novoCasal.setCasamento(casamento);
                    casais.add(novoCasal);
                    casamentos.put(partes[0], casamento);
                }

                Casamento.setIds(partes[0]);
            }
        }

        return casamentos;
    }

    public static void lerFestas(String caminhoArquivo, Map<String, Casamento> casamentos) throws IOException {

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");

                Casamento casamento = casamentos.get(partes[1]);

                if (casamento != null) {

                    int numConvidados = Integer.parseInt(partes[7]);
                    String[] convidados = new String[numConvidados];

                    for (int i = 0; i < numConvidados; i++) {
                        convidados[i] = partes[8 + i];
                    }

                    casamento.setFesta(new Festa(partes[0], partes[2], partes[3], partes[4],
                            Double.parseDouble((partes[5].replace(".", "")).replace(",", ".")),
                            Integer.parseInt(partes[6]), convidados));
                } else {
                    throw new IllegalArgumentException("Casamento não encontrado para a festa");
                }

                Festa.setIds(partes[0]);
            }
        }
    }

    public static void lerCompras(String caminhoArquivo, Map<String, Lar> lares, Map<String, Pessoa> pessoas)
            throws IOException {

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");

                for (Lar lar : lares.values()) {

                    Map<String, Tarefa> tarefas = lar.getTarefas();

                    if (tarefas.size() > 0) {
                        Tarefa tarefa = tarefas.get(partes[1]);
                        Loja loja = (Loja) pessoas.get(partes[2]);

                        if (tarefa != null && loja != null) {
                            tarefa.addCompras(
                                    new Compra(partes[0], partes[3], loja,
                                            Integer.parseInt(partes[4]),
                                            Integer.parseInt(partes[6]),
                                            Double.parseDouble(
                                                    (partes[5].replace(".", "")).replace(",", "."))));
                        }

                        Compra.setIds(partes[0]);
                    }
                }
            }
        }
    }
}
