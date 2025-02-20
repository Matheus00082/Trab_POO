package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class RelatorioPlanejamento {

    public static void imprimeRelatorio(Casal casal) {
        Date dataInicial = casal.calculaDataInicioPagamentos();
        int num_meses = casal.calculaNumMeses(dataInicial);
        Date dataAtual = dataInicial;

        String arquivo = "1-planejamento.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo, true))) {
            if (dataInicial == null) {
                writer.write("Casal com CPFs " + casal.getConjuge1().getCPF() + " e " + casal.getConjuge2().getCPF() +
                        " não possui gastos cadastrados.");
            } else {
                writer.write("Nome 1;Nome 2;");

                for (int i = 0; i < num_meses; i++) {
                    String mesAno = DataFunctions.getMesAno(dataAtual);
                    writer.write(mesAno);

                    if (i < num_meses - 1) {
                        writer.write(";");
                    } else {
                        writer.write("\n");
                    }

                    dataAtual = DataFunctions.incrementaMes(dataAtual);
                }

                writer.write(casal.getConjuge1().getNome() + ";" + casal.getConjuge2().getNome() + ";");

                for (int i = 0; i < num_meses; i++) {
                    casal.atualizaSaldoCasal(dataInicial);

                    String saldo = String.format("R$ %.2f", casal.getSaldo()).replace('.', ',');
                    writer.write(saldo);

                    writer.write(";");
                    dataInicial = DataFunctions.incrementaMes(dataInicial);
                }
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void geraRelatorioPlanejamento(List<Casal> casais) {
        Scanner scanner = new Scanner(System.in);

        while ((scanner.hasNextLine())) {
            String line = scanner.nextLine();

            if (line.isEmpty()) {
                break;
            }

            String[] cpfPair = line.split(",\\s*");
            if (cpfPair.length == 2) {
                for (Casal casal : casais) {
                    if (casal.ehIgualCasal(cpfPair[0], cpfPair[1])) {
                        imprimeRelatorio(casal);
                    }
                }
            }
        }

        scanner.close();
    }
}
