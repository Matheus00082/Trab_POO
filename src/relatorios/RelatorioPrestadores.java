package src.relatorios;

import src.pessoas.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class RelatorioPrestadores {

    public static void geraRelatorioPrestadores(Map<String, Pessoa> prestadores) {

        List<Pessoa> prestadoresList = new ArrayList<>(prestadores.values());

        prestadoresList.sort((p1, p2) -> {
            if(p1.getTipo().equals(p2.getTipo())) {
                return (int)(p2.getLucro() - p1.getLucro());
            }
            return p2.getTipo().compareTo(p1.getTipo());
        });

        for (Pessoa prestador : prestadoresList) {
            if (prestador.getLucro() > 0 || prestador.getTipo().equals("Loja")) {
                System.out.println(prestador.getTipo() + ";" + prestador.getNome() + ";R$ " + prestador.getLucro());
            }
        }
    }
}
