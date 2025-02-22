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

    public static void criaArquivosVazios(String caminho) throws IOException {

        try {
            new PrintWriter(caminho + "/saida/1-planejamento.csv").close();
            new PrintWriter(caminho + "/saida/2-estatisticas-prestadores.csv").close();
            new PrintWriter(caminho + "/saida/3-estatisticas-casais.csv").close();
        } catch (IOException e) {
            throw new IOException("Erro de I/O");
        }
    }

    public static Map<String, Pessoa> lerPessoas(String caminhoArquivo) throws Exception{
        Map<String, Pessoa> pessoas = new HashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");
                String tipo = partes[1].replace(" ", "");

                if (tipo.equals("F")) {

                    pessoas.put(partes[0].replace(" ", ""),
                            new PessoaFisica(partes[2], partes[4], partes[3], partes[0], partes[5], partes[6],
                                    Double.parseDouble(partes[7].replace(",", ".")),
                                    Double.parseDouble(partes[8].replace(",", ".")),
                                    Double.parseDouble(partes[9].replace(",", "."))));

                } else if (tipo.equals("J")) {

                    pessoas.put(partes[0].replace(" ", ""), new PessoaJuridica(partes[2], partes[4], partes[3], partes[0], partes[5]));

                } else if (tipo.equals("L")) {

                    pessoas.put(partes[0].replace(" ", ""), new Loja(partes[2], partes[4], partes[3], partes[0], partes[5]));

                }

                Pessoa.setIds(partes[0].replace(" ", ""));
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
                

                Lar.pessoaEstaCadastradaLar(pessoas, partes[1], partes[2], partes[0]);

                PessoaFisica conjuge1 = (PessoaFisica) pessoas.get(partes[1].replace(" ", ""));
                PessoaFisica conjuge2 = (PessoaFisica) pessoas.get(partes[2].replace(" ", ""));

                if (conjuge1 == null || conjuge2 == null) {
                    throw new IllegalArgumentException("Casal não encontrado");
                }

                Casal casal = new Casal(conjuge1, conjuge2);
                Lar lar = new Lar(partes[0], partes[3], Integer.parseInt(partes[4]), partes[5]);
                casal.setLar(lar);
                casais.add(casal);
                lares.put(partes[0].replace(" ", ""), lar);
                Lar.setIds(partes[0].replace(" ", ""));
            }
        }
        return lares;
    }

    public static Map<String, Tarefa> lerTarefas(String caminhoArquivo, Map<String, Pessoa> pessoas,
            Map<String, Lar> lares)
            throws Exception {
        Map<String, Tarefa> tarefas = new HashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                Pessoa trabalhador = (Pessoa) pessoas.get(partes[2].replace(" ", ""));

                Lar lar = lares.get(partes[1].replace(" ", ""));

                Tarefa.tarefaTemIdDeLarValido(lares, partes[1], partes[0]);
                Tarefa.prestadorEstaCadastrado(pessoas, partes[2], partes[0]);

                if (trabalhador instanceof PessoaFisica) {
                    trabalhador.setEhPrestador(true);
                }

                Tarefa t = new Tarefa(partes[0], partes[3], Integer.parseInt(partes[4]), Integer.parseInt(partes[6]),
                        Double.parseDouble((partes[5].replace(".", "")).replace(",", ".")), trabalhador);

                lar.getTarefas().put(partes[0].replace(" ", ""), t);
                tarefas.put(partes[0].replace(" ", ""), t);
            }
        }

        return tarefas;
    }

    public static Map<String, Casamento> lerCasamentos(String caminhoArquivo, Map<String, Pessoa> pessoas,
            List<Casal> casais) throws Exception {
        Map<String, Casamento> casamentos = new HashMap<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                Casamento casamento = new Casamento(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]);

                String idConjuge1 = partes[1].replace(" ", "");
                String idConjuge2 = partes[2].replace(" ", "");

                Casamento.pessoaEstaCadastradaCasamento(pessoas, idConjuge1, idConjuge2, partes[0]);

                boolean casalEncontrado = false;

                for (Casal casal : casais) {
                    String casalConjuge1 = casal.getConjuge1().getId().replace(" ", "");
                    String casalConjuge2 = casal.getConjuge2().getId().replace(" ", "");

                    if ((idConjuge1.equals(casalConjuge1) && idConjuge2.equals(casalConjuge2)) ||
                            (idConjuge1.equals(casalConjuge2) && idConjuge2.equals(casalConjuge1))) {

                        casal.setCasamento(casamento);
                        casamentos.put(partes[0].replace(" ", ""), casamento);
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
                    casamentos.put(partes[0].replace(" ", ""), casamento);
                }

                Casamento.setIds(partes[0].replace(" ", ""));
            }
        }

        return casamentos;
    }

    public static void lerFestas(String caminhoArquivo, Map<String, Casamento> casamentos) throws Exception {

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");

                Casamento casamento = casamentos.get(partes[1].replace(" ", ""));

                Festa.casamentoEstaCadastrado(casamentos, partes[1], partes[0]);

                int numConvidados = Integer.parseInt(partes[7]);
                String[] convidados = new String[numConvidados];

                for (int i = 0; i < numConvidados; i++) {
                    convidados[i] = partes[8 + i];
                }

                casamento.setFesta(new Festa(partes[0], partes[2], partes[3], partes[4],
                        Double.parseDouble((partes[5].replace(".", "")).replace(",", ".")),
                        Integer.parseInt(partes[6]), convidados));

                Festa.setIds(partes[0].replace(" ", ""));
            }
        }
    }

    public static void lerCompras(String caminhoArquivo, Map<String, Lar> lares, Map<String, Pessoa> pessoas,
            Map<String, Tarefa> t)
            throws IOException {

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");

                for (Lar lar : lares.values()) {

                    Map<String, Tarefa> tarefas = lar.getTarefas();

                    if (tarefas.size() > 0) {
                        Tarefa tarefa = tarefas.get(partes[1].replace(" ", ""));
                        Loja loja = null;

                        Compra.compraTemIdValidoDeTarefa(t, partes[1], partes[0]);
                        Compra.compraTemIdValidoDeLoja(pessoas, partes[2], partes[0]);
                        Compra.verificaSeEhPessoaJuridica(pessoas, partes[2], partes[0]);

                        Pessoa p = pessoas.get(partes[2].replace(" ", ""));
                        if (p instanceof Loja) {
                            loja = (Loja) p;
                        }

                        if (tarefa != null && loja != null) { // possível erro de leitura
                            tarefa.addCompras(
                                    new Compra(partes[0], partes[3], loja,
                                            Integer.parseInt(partes[4]),
                                            Integer.parseInt(partes[6]),
                                            Double.parseDouble(
                                                    (partes[5].replace(".", "")).replace(",", "."))));
                        }
                    }
                }
            }
        }
    }
}
