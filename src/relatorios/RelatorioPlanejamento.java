package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;

import java.util.Scanner;
import java.util.Date;
import java.util.Map;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;


public class RelatorioPlanejamento {

    // Método auxiliar para imprimir o relatório de planejamento de um casal
    private static void imprimeRelatorioPlanejamento(String caminho, Casal casal, String cpf1, String cpf2) throws Exception {
        
        String arquivo = caminho + "/saida/1-planejamento.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo, true))) {
            if (casal == null) {
                writer.write("Casal com CPFs " + cpf1 + " e " + cpf2 + " não está cadastrado.\n");
                return;
            }

            Date dataAtual = casal.calculaDataInicial();
            int numMeses = casal.getDuracaoParcelas();

            if (numMeses == 0) {
                writer.write("Casal com CPFs " + cpf1 + " e " + cpf2 + " não possui gastos cadastrados.\n");
                return;
            }

            // Imprime meses e anos MM/AAAA
            writer.write("Nome 1;Nome 2;");
            
            for (int i = 0; i < numMeses; i++) {
                String mesAno = GerenciadorDeDatas.getMesAno(dataAtual);
                writer.write(mesAno);

                if (i < numMeses - 1) {
                    writer.write(";");
                } else {
                    writer.write("\n");
                }

                dataAtual = GerenciadorDeDatas.incrementaMeses(dataAtual, 1);
            }

            // Imprime nomes e saldos
            writer.write(casal.getConjuge1().getNome() + ";" + casal.getConjuge2().getNome() + ";");

            for (int i = 1; i <= numMeses; i++) {
                String saldo = String.format("R$ %.2f;", casal.getSaldos().get(i)).replace('.', ',');
                writer.write(saldo);
            }   

            writer.write("\n"); 

        } catch (IOException e) {
           throw new IOException("Erro de I/O");
        }

    }

    public static void geraRelatorioPlanejamento(String caminho) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Map <String, Casal> casais = Casal.getCasais();

        for (Casal casal : casais.values()) {
            casal.calculaSaldosCasal();
        }

        while ((scanner.hasNextLine())) {
            String line = scanner.nextLine();
            String[] cpfPair = line.split(",");
            
            if (cpfPair.length == 2) {
                cpfPair[0] = cpfPair[0].replace(" ", "");
                cpfPair[1] = cpfPair[1].replace(" ", "");
                Casal casal = Casal.getCasalCadastrado(cpfPair[0], cpfPair[1]);
                imprimeRelatorioPlanejamento(caminho, casal, cpfPair[0], cpfPair[1]);
            }
        }

        scanner.close();
    }

}
