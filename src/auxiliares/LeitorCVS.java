package src.auxiliares;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import src.organizacao.*;
import src.pessoas.*;

public class LeitorCVS {

    public static void criaArquivosVazios(String caminho) throws IOException {
        try {
            new PrintWriter(caminho + "/saida/1-planejamento.csv").close();
            new PrintWriter(caminho + "/saida/2-estatisticas-prestadores.csv").close();
            new PrintWriter(caminho + "/saida/3-estatisticas-casais.csv").close();
        } catch (Exception e) {
            throw new IOException("Erro de I/O");
        }
    }

    // remove os espaços em branco de um array de strings
    public static String removeEspaços(String entrada) {
        entrada = entrada.replaceAll("\\s+", " ").trim();
        String[] campos = entrada.split(";");

        for (int i = 0; i < campos.length; i++) {
            campos[i] = campos[i].trim();

            // Verifica se o campo é uma data (formato dd/mm/aaaa)
            if (campos[i].matches(".*\\d{1,2}\\s*/\\s*\\d{1,2}\\s*/\\s*\\d{4}.*")) {
                campos[i] = campos[i].replaceAll("\\s*/\\s*", "/");
            }
            // Verifica se o campo é um valor monetário (formato 15000,00)
            else if (campos[i].matches(".*\\d+\\s*,\\s*\\d+.*")) {
                campos[i] = campos[i].replaceAll("\\s*,\\s*", ",");
            }
            // Verifica se o campo é um número com espaços (ex: "1 5")
            else if (campos[i].matches(".*\\d+\\s+\\d+.*")) {
                campos[i] = campos[i].replaceAll("\\s+", "");
            }
            // Verifica se o campo é uma hora (formato 14:30)
            else if (campos[i].matches(".*\\d{1,2}\\s*:\\s*\\d{2}.*")) {
                campos[i] = campos[i].replaceAll("\\s*:\\s*", ":");
            }
            // Garante que apenas um espaço seja permitido entre palavras
            else {
                campos[i] = campos[i].replaceAll("\\s+", " ");
            }
        }

        System.out.println(String.join(";", campos));
        return String.join(";", campos);
    };

    public static void lerPessoas(String caminho) throws Exception {

        String caminhoArquivo = caminho + "/pessoas.csv";

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            String linha = "";

            while ((linha = leitor.readLine()) != null) {
                String entrada = removeEspaços(linha), tipo;
                String[] campos = entrada.split(";");
                tipo = campos[1];

                @SuppressWarnings("unused")
                Pessoas pessoa = null; // Só retorna null se o tipo for inválido

                if (tipo.equals("F")) {
                    Double dinheiroGuardado = Double.parseDouble(campos[7].replace(" ", "").replace(".", "").replace(",", "."));
                    Double salario = Double.parseDouble(campos[8].replace(" ", "").replace(".", "").replace(",", "."));
                    Double gastosMensais = Double.parseDouble(campos[9].replace(" ", "").replace(".", "").replace(",", "."));
                    pessoa = new PessoaFisica(campos[0], campos[2], campos[3], campos[4], campos[5], campos[6],
                            dinheiroGuardado, salario, gastosMensais);
                } else if (tipo.equals("J")) {
                    pessoa = new PessoaJuridica(campos[0], campos[2], campos[3], campos[4], campos[5]);
                } else {
                    pessoa = new Loja(campos[0], campos[2], campos[3], campos[4], campos[5]);
                }
            }
        }
    };

    public static void lerLares(String caminho) throws Exception {

        String caminhoArquivo = caminho + "/lares.csv";

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            String linha = "";

            while ((linha = leitor.readLine()) != null) {
                String entrada = removeEspaços(linha);
                String[] campos = entrada.split(";");

                Lar.pessoaEstaCadastradaLar(campos[0], campos[1], campos[2]);

                PessoaFisica conjuge1 = (PessoaFisica) Pessoas.getPessoaCadastrada(campos[1]);
                PessoaFisica conjuge2 = (PessoaFisica) Pessoas.getPessoaCadastrada(campos[2]);

                Casal casal = new Casal(conjuge1, conjuge2);
                Lar lar = new Lar(campos[0], campos[3], Integer.parseInt(campos[4]), campos[5]);
                casal.setLar(lar);
            }
        }
    };

    public static void lerTarefas(String caminho) throws Exception {

        String caminhoArquivo = caminho + "/tarefas.csv";

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            String linha = "";

            while ((linha = leitor.readLine()) != null) {
                String entrada = removeEspaços(linha);
                String[] campos = entrada.split(";");

                Lar lar = Lar.getLarCadastrado(campos[1]);
                Pessoas prestador = Pessoas.getPessoaCadastrada(campos[2]);

                Tarefa.tarefaTemIdDeLarValido(campos[1], campos[0]);
                Tarefa.prestadorEstaCadastrado(campos[2], campos[0]);

                if (prestador instanceof PessoaFisica) {
                    prestador.setPrestador(true);
                }

                int prazoDias = Integer.parseInt(campos[4].replace(" ", ""));
                Double valor = Double.parseDouble(campos[5].replace(" ", "").replace(".", "").replace(",", "."));
                int parcelas = Integer.parseInt(campos[6].replace(" ", ""));

                Tarefa tarefa = new Tarefa(campos[0], prestador, campos[3], prazoDias, valor, parcelas);
                lar.addTarefa(campos[0], tarefa);
            }
        }
    };

    public static void lerCasamentos(String caminho) throws Exception {

        String caminhoArquivo = caminho + "/casamentos.csv";

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            String linha = "";

            while ((linha = leitor.readLine()) != null) {
                String entrada = removeEspaços(linha);
                String[] campos = entrada.split(";");

                Casamento.pessoaEstaCadastradaCasamento(campos[0], campos[1], campos[2]);

                Casamento casamento = new Casamento(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5]);
                PessoaFisica conjuge1 = (PessoaFisica) Pessoas.getPessoaCadastrada(campos[1]);
                PessoaFisica conjuge2 = (PessoaFisica) Pessoas.getPessoaCadastrada(campos[2]);
                Casal casal = Casal.getCasalCadastrado(conjuge1.getCPF(), conjuge2.getCPF());

                if (casal == null) {
                    casal = new Casal(conjuge1, conjuge2);
                }

                casal.setCasamento(casamento);
            }

        }
    };

    public static void lerFestas(String caminho) throws Exception {

        String caminhoArquivo = caminho + "/festas.csv";

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            String linha = "";

            while ((linha = leitor.readLine()) != null) {
                String entrada = removeEspaços(linha);
                String[] campos = entrada.split(";"), convidados;

                Festa.casamentoEstaCadastrado(campos[0], campos[1]);

                Casamento casamento = Casamento.getCasamentoCadastrado(campos[1]);
                int numeroConvidados = Integer.parseInt(campos[7].replace(" ", ""));
                convidados = new String[numeroConvidados];

                for (int i = 0; i < numeroConvidados; i++) {
                    convidados[i] = campos[8 + i];
                }

                int parcelas = Integer.parseInt(campos[6].replace(" ", ""));
                Double preco = Double.parseDouble(campos[5].replace(" ", "").replace(".", "").replace(",", "."));

                casamento.setFesta(new Festa(campos[0], campos[2], campos[3], campos[4], preco, parcelas,
                        convidados));
            }
        }
    };

    public static void lerCompras(String caminho) throws Exception {

        String caminhoArquivo = caminho + "/compras.csv";

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            String linha = "";

            while ((linha = leitor.readLine()) != null) {
                String entrada = removeEspaços(linha);
                String[] campos = entrada.split(";");
                
                Compra.compraTemIdValidoDeTarefa(campos[0], campos[1]);
                Compra.compraTemIdValidoDeLoja(campos[0], campos[2]);
                Compra.verificaSeEhPessoaJuridica(campos[0], campos[2]);

                Tarefa tarefa = Tarefa.getTarefaCadastrada(campos[1]);
                Loja loja = (Loja) Pessoas.getPessoaCadastrada(campos[2]);
                int qtd = Integer.parseInt(campos[4].replace(" ", ""));
                Double preco = Double.parseDouble(campos[5].replace(" ", "").replace(".", "").replace(",", "."));
                int parcelas = Integer.parseInt(campos[6].replace(" ", ""));

                tarefa.addCompra(new Compra(campos[0], loja, campos[6], qtd, preco, parcelas));
            }
        }
    };
}
