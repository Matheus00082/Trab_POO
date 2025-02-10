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

                    Lar lar = casal.getLar();
                    Casamento casamento = casal.getCasamento();

                    if (lar != null) {

                        int num_meses = lar.calculaNumMeses(casamento);

                        System.out.println(casal.getConjuge1().getNome() + "; " + casal.getConjuge2().getNome());

                        // Calcula saldo

                        while (num_meses > 0) {
                            Double saldo = casal.salarioTotal();
                            saldo -= casal.despesasMensais();
                            int parcelas = casamento.getFesta().getParcelas();

                            if (casamento.getFesta() != null) {
                                if (parcelas > 0) {
                                    saldo -= casamento.getFesta().getPrecoParcelado();
                                    casamento.getFesta().setParcelas(parcelas - 1);
                                }
                            }

                            for (Tarefa tarefa : lar.getTarefas()) {
                                int parcelas_tarefa = tarefa.getParcelas();

                                if (parcelas_tarefa > 0) {
                                    saldo -= tarefa.getValorParcelado();
                                    tarefa.setParcelas(parcelas_tarefa - 1);
                                }

                                for (Compra compra : tarefa.getCompras()) {
                                    int parcelas_compra = compra.getParcelas();
                                    if (parcelas_compra > 0) {
                                        saldo -= compra.getPrecoParcelado();
                                        compra.setParcelas(parcelas_compra - 1);
                                    }
                                }
                            }

                            saldo += casal.rendimentoPoupanca();
                            casal.setSaldo(saldo);

                            System.out.printf("R$ %.2f ", casal.getSaldo());
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
