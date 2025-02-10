package src.relatorios;

import src.organizacao.*;
import src.auxiliares.*;
import java.util.List;
import java.util.Scanner;

public class RelatorioPlanejamento {
    
    public static void geraRelatorioPlanejamento(List<Lar> lares) {

        Scanner s = new Scanner(System.in);

        while(s.hasNextLine()) {
            String linha =  s.nextLine();
            String[] cpfs = linha.split(",\\s*");

            int cont = 0;

            for (Lar lar : lares) {
                if (lar.casal.ehIgualCasal(cpfs[0], cpfs[1])) {
                    cont++;

                    int num_meses = lar.calculaNumMeses();

                    System.out.println(lar.casal.conjuge1.nome + "; " + lar.casal.conjuge2.nome);

                    // Calcula saldo

                    while (num_meses > 0) {
                        Double saldo = lar.casal.salarioTotal();
                        saldo -= lar.casal.despesasMensais();

                        if (lar.casamento.festa.parcelas > 0) {
                            saldo -= lar.casamento.festa.precoParcelado;
                            lar.casamento.festa.parcelas--;
                        }

                        for (Tarefa tarefa : lar.tarefas) {
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

                        saldo += lar.casal.rendimentoPoupanca();
                        lar.casal.saldo += saldo;  

                        System.out.printf("R$ %.2f ", lar.casal.saldo);
                        num_meses--;
                    }

                    System.out.println();
                } 
            }

            if (cont == 0) {
                System.out.println("Casal com CPFs " + cpfs[0] + " e " + cpfs[1] + " não está cadastrado.");
            }
        }

        s.close();

    }
}
