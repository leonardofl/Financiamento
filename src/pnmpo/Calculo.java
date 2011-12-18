package pnmpo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Calculo {

	private static double TotalJur;
	
	/**
	 * 
	 * @param ValContr valor contratado (PV)
	 * @param ValBContr valor base contratado (valor contratado + IOF + JurosCarencia)
	 * @param NumPrest numero de prestações contratado (N)
	 * @param TaxaJuros taxa de juros contratada (I)
	 * @param DataPagamento data do pagamento pretendida
	 * @param DataContratacao data em que a contratacao foi/sera feita
	 * @param DiaDebito dia do mes para o debito das parcelas
	 */
	public static boolean checkConsistencia(double ValContr, double ValBContr, int NumPrest, double TaxaJuros, Date DataPagamento, Date DataContratacao, int DiaDebito) {
	
		return true;
	}

	
	/**
	 * 
	 * @param DataContratacao data em que a contratacao foi/sera feita
	 * @param DiaDebito dia do mes para o debito das parcelas
	 */
	public static Date calculaDataBase(Date DataContratacao, int DiaDebito) {
		
		Date DataBase;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(DataContratacao);		
		int diaContratacao = cal.get(Calendar.DAY_OF_MONTH);
		int mesContratacao = cal.get(Calendar.MONTH);
		
		if (DiaDebito < diaContratacao) 
			cal.set(Calendar.MONTH, mesContratacao + 1);
		cal.set(Calendar.DAY_OF_MONTH, DiaDebito); // Data1Parc
		DataBase = cal.getTime();
				
		return DataBase;
	}

	public static Date primeiraParcela(Date DataContratacao, int DiaDebito) {
		
		Calendar calDataContratacao = Calendar.getInstance();
		calDataContratacao.setTime(DataContratacao);		
		int mesContratacao = calDataContratacao.get(Calendar.MONTH);
		int anoContratacao = calDataContratacao.get(Calendar.YEAR);
		
		if (mesContratacao < 12)  // Se o mes da contratação for anterior a dezembro
		    calDataContratacao.set(Calendar.MONTH, mesContratacao++); // recebe o mes seguinte
		else { // se for dezembro
			mesContratacao = 0;
			calDataContratacao.set(Calendar.MONTH, mesContratacao); // recebe janeiro
			calDataContratacao.set(Calendar.YEAR, anoContratacao++); 
		}
		
		Calendar calData1Parc = Calendar.getInstance();
		calData1Parc.setTime(DataContratacao);
		calDataContratacao.set(Calendar.DAY_OF_MONTH, DiaDebito);
		Date Data1Parc = calDataContratacao.getTime();
		
		int dif = (int) ((Data1Parc.getTime() - DataContratacao.getTime()) / 86400000);
		if (dif < 30) {
			
		    if (mesContratacao < 11) { // Se o mes da contratação for anterior a dezembro
		    	calData1Parc.set(Calendar.DAY_OF_MONTH, DiaDebito);
		    	calData1Parc.set(Calendar.MONTH, mesContratacao + 1);
		    	calData1Parc.set(Calendar.YEAR, anoContratacao);
		    }
		    else { // se for dezembro
		    	calData1Parc.set(Calendar.DAY_OF_MONTH, DiaDebito);
		    	calData1Parc.set(Calendar.MONTH, 0);
		    	calData1Parc.set(Calendar.YEAR, anoContratacao + 1);
		    }
		}
		
		Data1Parc = calData1Parc.getTime();
		
		return Data1Parc;
	}
	
	public static int calculaDiasCarencia(Date DataContratacao, int DiaDebito) {

		int DiasCarencia;
		Date DataBase = calculaDataBase(DataContratacao, DiaDebito);
		
		DiasCarencia = (int) ((DataBase.getTime() - DataContratacao.getTime()) / 86400000);
		
		return DiasCarencia;
	}
	
	public static double calculaIOF(double ValContr) {
		
		double IOF = ValContr * 0.0038;
		IOF = (ValContr + IOF) * 0.0038;		
		
		return IOF;
	}
	
	public static double calculaJurosCarencia(double ValContr, double TaxaJuros, Date DataContratacao, int DiaDebito) {
		
	    // Juros de carencia = valor financiado vezes modulo de X
	    // X = ((1 + Taxa de juros) ^ (dias corridos/30)) - 1

		TaxaJuros = TaxaJuros / 100;
		double IOF = calculaIOF(ValContr);
		int DiasCarencia = calculaDiasCarencia(DataContratacao, DiaDebito);
		
		// JCarencia = ((ValContr + IOF) * (((1 + TaxaJuros) ^ DataBase - DataContratacao) - 1))
		double JCarencia = ((ValContr + IOF) * ((Math.pow((1 + TaxaJuros) , ( (double) DiasCarencia / 30))) - 1));

		return JCarencia;
	}
	
	public static double calculaPrimeiraPrestacao(double ValContr, int NumPrest, double TaxaJuros, Date DataContratacao, int DiaDebito) {

		TaxaJuros = TaxaJuros / 100;
		Date DataBase = calculaDataBase(DataContratacao, DiaDebito);
		
		Calendar calDataContrat = Calendar.getInstance();
		calDataContrat.setTime(DataBase);
		Calendar calDataBase = Calendar.getInstance();
		calDataBase.setTime(DataBase);
				
		// calcula PMT    ' PMT = PV * (I * ((1+I) ^ (NumPrest + (Dias de Carencia/30))/((1+I)^N-1))))
		// PMT = PV * (I * ((1+I) ^ (NumPrest)/((1+I)^N-1))))
		// PMT = PV * (I * (I2 ^ (NNumPrest + N2) / N3)))
		// PMT = PV * (I * (I2 ^ ( N / N3)))
		// PMT = PV * (I * (I2 ^ N4))


		double I2 = 1 + TaxaJuros; // ok conforme planilha
		
		int dif = (int) ((DataBase.getTime() - DataContratacao.getTime()) / 86400000);
		double N2 = (double) dif / 30;
		double N = NumPrest + N2;
		double N3 = Math.pow(I2, NumPrest) - 1;
		
		double N4 = N / N3; // erro
		
		double ValPrest = Math.pow(I2, N4); // ?_?" (linha inútil)
		double IOF = calculaIOF(ValContr);
		ValPrest = (ValContr + IOF) * (TaxaJuros * Math.pow(I2, N4));
		//Dim a As Variant
		// double a = Math.pow(I2, N4); // variável inútil "u.u
		
		ValPrest = (ValContr + IOF) * (TaxaJuros * (Math.pow(I2, N) / N3));

		ValPrest = Math.rint(ValPrest * 100) / 100;
		return ValPrest;
	}
	
	public static List<Parcela> calculaTabelaPrice(double ValContr, int NumPrest, double TaxaJuros, Date DataContratacao, int DiaDebito) {
		
		// o retorno tem esse nome pra fazer analogia com antigo sistema
		// o resultado dessas contas, ele jogava direto numa tabela chamada Price
		List<Parcela> price = new ArrayList<Parcela>();

		double IOF = calculaIOF(ValContr);
		double JCarencia = calculaJurosCarencia(ValContr, TaxaJuros, DataContratacao, DiaDebito);
		TaxaJuros = TaxaJuros / 100;
		Date Data1Parc = primeiraParcela(DataContratacao, DiaDebito);
		double I2 = 1 + TaxaJuros;
		
		// Saldo devedor inicialmente recebe o valor que se pretende contratar, 
		// para fazer a simulação
		double SalDev = (ValContr + IOF + JCarencia); 
		
		int NumPrestI = 1;
		double CalcProxMes = SalDev * I2;
		
		Calendar calData1Parc = Calendar.getInstance();
		calData1Parc.setTime(Data1Parc);
		int DiaP = calData1Parc.get(Calendar.DAY_OF_MONTH);
		int MesP = calData1Parc.get(Calendar.MONTH);
		int AnoP = calData1Parc.get(Calendar.YEAR);

		// O calculo da primeira parcela é feito aa parte, 
		// por existir a peculiaridade da cobrança do juros de carencia.
		double ValPrest = calculaPrimeiraPrestacao(ValContr, NumPrest, TaxaJuros*100, DataContratacao, DiaDebito);

		
		
		double ValJuros = CalcProxMes - SalDev + JCarencia; // ok
		double Amortizacao = ValPrest - ValJuros; // ok
		SalDev = CalcProxMes - ValPrest;
		CalcProxMes = SalDev * I2;
		TotalJur = 0; // essa linha não existia
		// mas no código anterior não havia referência à TotalJur antes deste ponto!
		TotalJur = TotalJur + ValJuros;

		// inclui novo registro
		Parcela parcela = new Parcela();
		parcela.setSequencia(NumPrestI);
		parcela.setJuros(ValJuros);
		parcela.setAmortizado(Amortizacao);
		parcela.setSaldoDevedor(SalDev);
		Calendar calP = Calendar.getInstance();
		calP.set(Calendar.DAY_OF_MONTH, DiaP);
		calP.set(Calendar.MONTH, MesP);
		calP.set(Calendar.YEAR, AnoP);
		parcela.setData(calP.getTime());
		price.add(parcela);

		NumPrestI = NumPrestI + 1;
		MesP = MesP + 1;

		// Inicia-se o calculo das parcelas restantes.
		while (NumPrestI <= NumPrest) {

		    ValJuros = CalcProxMes - SalDev;
		    TotalJur = TotalJur + ValJuros;
		    Amortizacao = ValPrest - ValJuros;
		    SalDev = SalDev - Amortizacao;
		    CalcProxMes = SalDev * I2;
		    
		    // inclui novo registro
		    parcela = new Parcela();  
			parcela.setSequencia(NumPrestI);
			parcela.setJuros(ValJuros);
			parcela.setAmortizado(Amortizacao);
			parcela.setSaldoDevedor(SalDev);
			calP.set(Calendar.DAY_OF_MONTH, DiaP);
			calP.set(Calendar.MONTH, MesP);
			calP.set(Calendar.YEAR, AnoP);
			parcela.setData(calP.getTime());
			price.add(parcela);

		    if (MesP < 11)
		        MesP = MesP + 1;
		    else {
		        MesP = 0;
		        AnoP = AnoP + 1;
		    }
		        
		    NumPrestI = NumPrestI + 1;
			
		}
		
		return price;
	}
	
	/**
	 * Só pode ser usado depois do método calculaTablePrice
	 * @return
	 */
	public static double getTotalJur() {
		
		return TotalJur;
	}
}
