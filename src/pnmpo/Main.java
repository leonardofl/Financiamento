package pnmpo;

import java.util.Calendar;
import java.util.Date;

public class Main {
	
	public static void main(String[] args) {
		
		Formulario form = new Formulario("Pnmpo");
		form.exibir();
		form.preencher();
		
		//testeDate();
	}
	
	public static void testeDate() {
		
		String str = new String("13/01/2009");
		int dia = Integer.parseInt(str.substring(0, 2));
		int mes = Integer.parseInt(str.substring(3, 5));
		int ano = Integer.parseInt(str.substring(6, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mes-1, dia);
		Date data = cal.getTime();
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(data);
		int day = cal2.get(Calendar.DAY_OF_MONTH);
		int month = cal2.get(Calendar.MONTH);
		int year = cal2.get(Calendar.YEAR);
		Date date2 = cal2.getTime();
		
		System.out.println(str);
		System.out.println(dia + "/" + mes + "/" + ano);
		System.out.println(day + "/" + month + "/" + year);
		System.out.println(data);
		System.out.println(date2);
	}
	
}
