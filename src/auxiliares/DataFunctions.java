package src.auxiliares;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataFunctions {

    public static boolean ehMesmoMeseAno(Date data1, Date data2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String mesAno1 = null, mesAno2 = null;

        try {
            mesAno1 = sdf.format(data1);
            mesAno2 = sdf.format(data2);
        } catch (Exception e) {
            throw new ParseException("Erro ao formatar a data", 0);
        }

        if (mesAno1.equals(mesAno2)) {
            return true;
        }

        return false;
    }

    public static boolean verificaSeEhPosterior(Date dataDeInicio, Date dataParaComparar) throws ParseException {
        int anoInicio = DataFunctions.getAno(dataDeInicio);
        int anoAtual = DataFunctions.getAno(dataParaComparar);
        int mesInicio = DataFunctions.getMes(dataDeInicio);
        int mesAtual = DataFunctions.getMes(dataParaComparar);

        if (((anoAtual > anoInicio) || (anoAtual == anoInicio && mesAtual >= mesInicio))) {
            return true;
        }

        return false;
    }

    public static int mesesEntreDatas(Date data1, Date data2) throws ParseException {

        if (!verificaSeEhPosterior(data1, data2)) {
            return 0;
        }

        int ano1 = getAno(data1);
        int ano2 = getAno(data2);
        int mes1 = getMes(data1);
        int mes2 = getMes(data2);

        int meses = 0;

        if (ano1 == ano2) {
            meses = mes2 - mes1;
        } else {
            meses = (ano2 - ano1) * 12;
            meses += mes2 - mes1;
        }

        return meses;
    }

    public static int getAno(Date data) throws ParseException {
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        String dateAno = null;
        try {
            dateAno = sdfAno.format(data);
        } catch (Exception e) {
            throw new ParseException("Erro ao formatar a data", 0);
        }

        int ano = Integer.parseInt(dateAno);

        return ano;
    }

    public static int getMes(Date data) throws ParseException {
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
        String dateMes = null;
        int mes = 0;

        try {
            dateMes = sdfMes.format(data);
            mes = Integer.parseInt(dateMes);
        } catch (Exception e) {
            throw new  ParseException("Erro ao formatar a data", 0);
        }

        return mes;
    }

    public static String getMesAno(Date data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String mesAno = null;

        try {
            mesAno = sdf.format(data);
        } catch (Exception e) {
            throw new ParseException("Erro ao formatar a data", 0);
        }

        return mesAno;
    }

    public static Date incrementaMes(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, 1);

        return cal.getTime();
    }
}
