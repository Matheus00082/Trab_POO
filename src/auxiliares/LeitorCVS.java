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

    public static String[] tiraEspacos(String linha, char tipo) {
        String[] partes = linha.split(";");

        partes[0] = partes[0].replace(" ", "");

        if (tipo == 'P') {
            partes[1] = partes[1].replace(" ", "");
            partes[2] = partes[2].trim();
            partes[3] = partes[3].trim();
            partes[4] = partes[4].trim();
            partes[5] = partes[5].replace(" ", "");

            if (partes[1] == "F") {
                partes[6] = partes[6].replace(" ", "");
                partes[7] = partes[7].replace(" ", "");
                partes[8] = partes[8].replace(" ", "");
                partes[9] = partes[9].replace(" ", "");
            } 
        } else if (tipo == 'L') {
            partes[1] = partes[1].replace(" ", "");
            partes[2] = partes[2].replace(" ", "");
            partes[3] = partes[3].trim();
            partes[4] = partes[4].trim();
            partes[5] = partes[5].replace(" ", "");
        } else if (tipo == 'C') {
            partes[1] = partes[1].replace(" ", "");
            partes[2] = partes[2].replace(" ", "");
            partes[3] = partes[3].replace(" ", "");
            partes[4] = partes[4].replace(" ", "");
            partes[5] = partes[5].trim();
        } else if (tipo == 'T') {
            partes[1] = partes[1].replace(" ", "");
            partes[2] = partes[2].replace(" ", "");
            partes[3] = partes[3].replace(" ", "");
            partes[4] = partes[4].replace(" ", "");
            partes[5] = partes[5].replace(" ", "");
            partes[6] = partes[6].replace(" ", "");
        } else if (tipo == 'F') {
            partes[1] = partes[1].replace(" ", "");
            partes[2] = partes[2].trim();
            partes[3] = partes[3].replace(" ", "");
            partes[4] = partes[4].replace(" ", "");
            partes[5] = partes[5].replace(" ", "");
            partes[6] = partes[6].replace(" ", "");
            partes[7] = partes[7].replace(" ", "");

            for (int i = 8; i < partes.length; i++) {
                partes[i] = partes[i].replaceAll(" ", "");
            }

        } else {
            partes[1] = partes[1].replace(" ", "");
            partes[2] = partes[2].replace(" ", "");
            partes[3] = partes[3].trim();
            partes[4] = partes[4].replace(" ", "");
            partes[5] = partes[5].replace(" ", "");
            partes[6] = partes[6].replace(" ", "");
        }

        return partes;
    };

    public static void lerPessoas(String caminho) throws Exception {

        String caminhoArquivo = caminho + "/pessoas.csv";

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            String linha = "";

            while ((linha = leitor.readLine()) != null) {
                String[] campos = tiraEspacos(linha, 'P');

                @SuppressWarnings("unused")
                Pessoas pessoa = null; // Só retorna null se o tipo for inválido

                if (campos[1].equals("F")) {
                    Double dinheiroGuardado = Double.parseDouble(campos[7].replace(".", "").replace(",", "."));
                    Double salario = Double.parseDouble(campos[8].replace(".", "").replace(",", "."));
                    Double gastosMensais = Double.parseDouble(campos[9].replace(".", "").replace(",", "."));
                    pessoa = new PessoaFisica(campos[0], campos[2], campos[3], campos[4], campos[5], campos[6],
                            dinheiroGuardado, salario, gastosMensais);
                } else if (campos[1].equals("J")) {
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
                String[] campos = tiraEspacos(linha, 'L');

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
                String[] campos = tiraEspacos(linha, 'T');

                Lar lar = Lar.getLarCadastrado(campos[1]);
                Pessoas prestador = Pessoas.getPessoaCadastrada(campos[2]);

                Tarefa.tarefaTemIdDeLarValido(campos[1], campos[0]);
                Tarefa.prestadorEstaCadastrado(campos[2], campos[0]);

                if (prestador instanceof PessoaFisica) {
                    prestador.setPrestador(true);
                }

                int prazoDias = Integer.parseInt(campos[4]);
                Double valor = Double.parseDouble(campos[5].replace(".", "").replace(",", "."));
                int parcelas = Integer.parseInt(campos[6]);

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
                String[] campos = tiraEspacos(linha, 'C');

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
                String[] campos = tiraEspacos(linha, 'F'), convidados;

                Festa.casamentoEstaCadastrado(campos[0], campos[1]);

                Casamento casamento = Casamento.getCasamentoCadastrado(campos[1]);
                int numeroConvidados = Integer.parseInt(campos[7]);
                convidados = new String[numeroConvidados];

                for (int i = 0; i < numeroConvidados; i++) {
                    convidados[i] = campos[8 + i];
                }

                int parcelas = Integer.parseInt(campos[6]);
                Double preco = Double.parseDouble(campos[5].replace(".", "").replace(",", "."));

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
                String[] campos = tiraEspacos(linha, 'C');
                
                Compra.compraTemIdValidoDeTarefa(campos[0], campos[1]);
                Compra.compraTemIdValidoDeLoja(campos[0], campos[2]);
                Compra.verificaSeEhPessoaJuridica(campos[0], campos[2]);

                Tarefa tarefa = Tarefa.getTarefaCadastrada(campos[1]);
                Loja loja = (Loja) Pessoas.getPessoaCadastrada(campos[2]);
                int qtd = Integer.parseInt(campos[4]);
                Double preco = Double.parseDouble(campos[5].replace(".", "").replace(",", "."));
                int parcelas = Integer.parseInt(campos[6]);

                tarefa.addCompra(new Compra(campos[0], loja, campos[6], qtd, preco, parcelas));
            }
        }
    };
}
