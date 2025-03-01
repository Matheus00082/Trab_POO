package src.auxiliares;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class GerenciadorDeDatas {

    public static Date incrementaMeses(Date data, int numMeses) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, numMeses);

        return cal.getTime();
    }

    // Retorna true se a data1 for anterior a data2
    public static int comparaDatas(Date data1, Date data2) throws ParseException {
        int anoInicio = getAno(data1);
        int anoAtual = getAno(data2);
        int mesInicio = getMes(data1);
        int mesAtual = getMes(data2);

        if (((anoAtual > anoInicio) || (anoAtual == anoInicio && mesAtual >= mesInicio))) {
            return -1;
        } else if (((anoInicio > anoAtual) || (anoAtual == anoInicio && mesInicio >= mesAtual))) {
            return 1;
        }

        return 0;
    }

    // Retorna a quantidade de meses entre duas datas se a data1 for menor que a data2
    public static int mesesEntreDatas(Date data1, Date data2) throws ParseException {

        if (comparaDatas(data1, data2) >= 0) {
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

    public static boolean ehMesmoMeseAno(Date data1, Date data2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String mesAno1 = null, mesAno2 = null;

        try {
            mesAno1 = sdf.format(data1);
            mesAno2 = sdf.format(data2);
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }

        return mesAno1.equals(mesAno2);
    }

    public static int getAno(Date data) throws ParseException {
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        String dateAno = null;
        try {
            dateAno = sdfAno.format(data);
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
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
            throw new  ParseException("Erro de formatação", 0);
        }

        return mes;
    }

    public static String getMesAno(Date data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String mesAno = null;

        try {
            mesAno = sdf.format(data);
        } catch (Exception e) {
            throw new ParseException("Erro de formatação", 0);
        }

        return mesAno;
    }

}
