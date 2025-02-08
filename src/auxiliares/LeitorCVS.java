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
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.ISO_8859_1))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] partes = linha.split(";");
                String tipo = partes[1];

                if (tipo.equals("F")) {
                    pessoas.add(new PessoaFisica(partes[2], partes[3], partes[4], partes[0], partes[5], partes[6],
                            Double.parseDouble(partes[7].replace(",", ".")),
                            Double.parseDouble(partes[8].replace(",", ".")),
                            Double.parseDouble(partes[9].replace(",", "."))));
                } else if (tipo.equals("J") || tipo.equals("L")) {
                    pessoas.add(new PessoaJuridica(partes[2], partes[3], partes[4], partes[0], partes[5]));
                }
            }
        }
        return pessoas;
    }

    public static List<Lar> lerLares(String caminhoArquivo, List<Pessoa> pessoas) throws IOException {
        List<Lar> lares = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), StandardCharsets.ISO_8859_1))) {

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

                lares.add(new Lar(partes[0], conjuge1, conjuge2, partes[3], Integer.parseInt(partes[4]), partes[5]));
            }
        }
        return lares;
    }

}
