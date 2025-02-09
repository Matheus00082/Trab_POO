package src.relatorios;

import src.pessoas.*;
import java.util.List;

public class RelatorioPrestadores {

    public static void geraRelatorioPrestadores(List<Pessoa> prestadores) {

        prestadores.sort((p1, p2) -> {
            if(p1.tipo.equals(p2.tipo)){
                return (int)(p2.lucro - p1.lucro);
            }
            return p2.tipo.compareTo(p1.tipo);
        });

        for (Pessoa prestador : prestadores) {
            if (prestador.lucro > 0 || prestador.tipo.equals("Loja")) {
                System.out.println(prestador.tipo + ";" + prestador.nome + ";R$ " + prestador.lucro);
            }
        }
    }
}
