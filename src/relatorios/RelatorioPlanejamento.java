package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;
import java.util.List;
import java.util.Scanner;

public class RelatorioPlanejamento {

    public static void geraRelatorioPlanejamento(List<Casal> casais) {

        Scanner s = new Scanner(System.in);

        while (s.hasNextLine()) {
            String linha = s.nextLine();
            String[] cpfs = linha.split(",\\s*");

            int cont = 0;

            for (Casal casal : casais) {
                if (casal.ehIgualCasal(cpfs[0], cpfs[1])) {
                    cont++;

                    if (casal.lar != null) {

                        int num_meses = casal.lar.calculaNumMeses(casal.casamento);

                        System.out.println(casal.conjuge1.nome + "; " + casal.conjuge2.nome);

                        // Calcula saldo

                        while (num_meses > 0) {
                            Double saldo = casal.salarioTotal();
                            saldo -= casal.despesasMensais();

                            if (casal.casamento.festa != null) {
                                if (casal.casamento.festa.parcelas > 0) {
                                    saldo -= casal.casamento.festa.precoParcelado;
                                    casal.casamento.festa.parcelas--;
                                }
                            }

                            for (Tarefa tarefa : casal.lar.tarefas) {
                                if (tarefa.parcelas > 0) {
                                    saldo -= tarefa.valorParcelado;
                                    tarefa.parcelas--;
                                }

                                for (Compra compra : tarefa.compras) {
                                    if (compra.parcelas > 0) {
                                        saldo -= compra.precoParcelado;
                                        compra.parcelas--;
                                    }
                                }
                            }

                            saldo += casal.rendimentoPoupanca();
                            casal.saldo += saldo;

                            System.out.printf("R$ %.2f ", casal.saldo);
                            num_meses--;
                        }

                        System.out.println();
                    }
                }
            }

            if (cont == 0) {
                System.out.println("Casal com CPFs " + cpfs[0] + " e " + cpfs[1] + " não está cadastrado.");
            }
        }

        s.close();

    }
}
