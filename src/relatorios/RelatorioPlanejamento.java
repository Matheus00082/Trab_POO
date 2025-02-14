package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class RelatorioPlanejamento {

    public static void imprimeRelatorio(Casal casal) {
        int num_meses = casal.calculaNumMeses();         
        Date dataInicial = casal.calculaDataInicioPagamentos();  
        Date dataAtual = dataInicial;  

        System.out.print("Nome 1;Nome 2;");

        for (int i = 0; i < num_meses; i++) {
            String mesAno = DataFunctions.getMesAno(dataAtual);
            System.out.print(mesAno);

            if (i < num_meses-1) {
                System.out.print(";");
            } else {
                System.out.println();
            }

            dataAtual = DataFunctions.incrementaMes(dataAtual);
        }

        System.out.print(casal.getConjuge1().getNome() + ";" + casal.getConjuge2().getNome() + ";");

        for (int i = 0; i < num_meses; i++) {
            casal.atualizaSaldoCasal(dataInicial);
            System.out.printf("%.2f", casal.getSaldo());

            if (i < (num_meses - 1)) {
                System.out.print(";");
            }
            dataInicial = DataFunctions.incrementaMes(dataInicial);
        }

        System.out.println();
    }

    public static void geraRelatorioPlanejamento(List<Casal> casais) {

        Scanner s = new Scanner(System.in);

        while (s.hasNextLine()) {
            String linha = s.nextLine();
            String[] cpfs = linha.split(",\\s*");

            int cont = 0;

            for (Casal casal : casais) {
                if (casal.ehIgualCasal(cpfs[0], cpfs[1])) {
                    cont++;
                    imprimeRelatorio(casal);
                }
            }

            if (cont == 0) {
                System.out.println("Casal com CPFs " + cpfs[0] + " e " + cpfs[1] + " não está cadastrado.");
            }
        }

        s.close();
    }
}
