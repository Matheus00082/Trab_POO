package src.auxiliares;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataFunctions {
    
    public static boolean ehMesmoMeseAno(Date data1, Date data2) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

        String mesAno1 = sdf.format(data1);
        String mesAno2 = sdf.format(data2);

        if (mesAno1.equals(mesAno2)) {
            return true;
        }

        return false;
    }

    public static boolean verificaSeEhPosterior(Date dataDeInicio, Date dataParaComparar) {
        int anoInicio = DataFunctions.getAno(dataDeInicio);
        int anoAtual = DataFunctions.getAno(dataParaComparar);
        int mesInicio = DataFunctions.getMes(dataDeInicio);
        int mesAtual = DataFunctions.getMes(dataParaComparar);

        if (((anoAtual > anoInicio) || (anoAtual == anoInicio && mesAtual >= mesInicio))) {
            return true;
        }

        return false;
    }

    public static int getAno(Date data) {
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        int ano = Integer.parseInt(sdfAno.format(data));

        return ano;
    }

    public static int getMes(Date data) {
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
        int mes = Integer.parseInt(sdfMes.format(data));

        return mes;
    }

    public static String getMesAno(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String mesAno = sdf.format(data);

        return mesAno;
    }

    public static Date incrementaMes(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, 1);

        return cal.getTime();
    }
}
