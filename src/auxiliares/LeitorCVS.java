package src.auxiliares;

import src.pessoas.*;
import src.organizacao.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LeitorCVS {

    public static List<Pessoa> lerPessoas(String caminhoArquivo) throws IOException {
        List<Pessoa> pessoas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");
                String tipo = partes[1];

                if (tipo.equals("F")) {
                    pessoas.add(new PessoaFisica(partes[2], partes[4], partes[3], partes[0], partes[5], partes[6],
                            Double.parseDouble(partes[7].replace(",", ".")),
                            Double.parseDouble(partes[8].replace(",", ".")),
                            Double.parseDouble(partes[9].replace(",", "."))));
                } else if (tipo.equals("J")) {
                    pessoas.add(new PessoaJuridica(partes[2], partes[4], partes[3], partes[0], partes[5]));
                } else if (tipo.equals("L")) {
                    pessoas.add(new Loja(partes[2], partes[4], partes[3], partes[0], partes[5]));
                }
            }
        }
        return pessoas;
    }

    public static List<Lar> lerLares(String caminhoArquivo, List<Pessoa> pessoas, List<Casal> casais) throws IOException {
        List<Lar> lares = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                PessoaFisica conjuge1 = null;
                PessoaFisica conjuge2 = null;

                for (Pessoa pessoa : pessoas) {
                    if (pessoa.id.equals(partes[1])) {
                        conjuge1 = (PessoaFisica) pessoa;
                    } else if (pessoa.id.equals(partes[2])) {
                        conjuge2 = (PessoaFisica) pessoa;
                    }
                }

                Casal casal = new Casal(conjuge1, conjuge2);
                casal.lar = new Lar(partes[0], partes[3], Integer.parseInt(partes[4]), partes[5]);
                casais.add(casal);
                lares.add(casal.lar);
            }
        }
        return lares;
    }

    public static List<Tarefa> lerTarefas(String caminhoArquivo, List<Pessoa> pessoas, List<Lar> lares) throws IOException {
        List<Tarefa> tarefas = new ArrayList<>();    

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                Pessoa trabalhador = null;
                Lar lar = null;

                for (Pessoa pessoa : pessoas) {
                    if (pessoa.id.equals(partes[2])) {
                        trabalhador = pessoa;
                    }
                }

                for (Lar l : lares) {
                    if (l.idLar.equals(partes[1])) {
                        lar = l;
                    }
                }

                Tarefa t = new Tarefa(partes[0], partes[3], Integer.parseInt(partes[4]), Integer.parseInt(partes[6]),
                Double.parseDouble((partes[5].replace(".", "")).replace(",", ".")), trabalhador);

                lar.tarefas.add(t);

                tarefas.add(t);
            }
        }

        return tarefas;
    }

    public static List<Casamento> lerCasamentos(String caminhoArquivo, List<Casal> casais) throws IOException {
        List<Casamento> casamentos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");

                for (Casal casal : casais) {
                    if (casal.conjuge1.id.equals(partes[1]) && casal.conjuge2.id.equals(partes[2]) ||
                            casal.conjuge1.id.equals(partes[2]) && casal.conjuge2.id.equals(partes[1])) {
                        casal.casamento = new Casamento(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]);
                        casamentos.add(casal.casamento);
                    }
                }
            }
        }

        return casamentos;
    }

    public static void lerFestas(String caminhoArquivo, List<Casal> casais) throws IOException {

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");

                for (Casal casal : casais) {
                    if (casal.casamento != null) {
                        if (casal.casamento.idCasamento.equals(partes[1])) {

                            int numConvidados = Integer.parseInt(partes[7]);
                            String[] convidados = new String[numConvidados];

                            for (int i = 0; i < numConvidados; i++) {
                                convidados[i] = partes[8 + i];
                            }

                            casal.casamento.festa = new Festa(partes[0], partes[2], partes[3], partes[4],
                                    Double.parseDouble((partes[5].replace(".", "")).replace(",", ".")),
                                    Integer.parseInt(partes[6]), convidados);
                        }
                    }
                }

            }
        }
    }

    public static void lerCompras(String caminhoArquivo, List<Lar> lares, List<Pessoa> pessoas)
            throws IOException {

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.UTF_8))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");

                for (Lar lar : lares) {
                    if (lar.tarefas.size() > 0) {
                        for (Tarefa tarefa : lar.tarefas) {
                            if (tarefa.idTarefa.equals(partes[1])) {
                                for (Pessoa pessoa : pessoas) {
                                    if (pessoa.id.equals(partes[2])) {
                                        tarefa.compras.add(
                                                new Compra(partes[0], partes[3], (Loja) pessoa,
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
        }
    }
}
