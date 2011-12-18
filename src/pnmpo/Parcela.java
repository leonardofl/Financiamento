package pnmpo;

import java.util.Date;

public class Parcela {

	private int sequencia;
	private double juros;
	private double amortizacao;
	private double saldoDevedor;
	private Date data;
	
	public Parcela() {
		
	}

	public Parcela(int sequencia, double juros, double amortizacao,
			double saldoDevedor, Date data) {
		super();
		this.sequencia = sequencia;
		this.juros = juros;
		this.amortizacao = amortizacao;
		this.saldoDevedor = saldoDevedor;
		this.data = data;
	}

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public double getJuros() {
		return juros;
	}

	public void setJuros(double juros) {
		this.juros = juros;
	}

	public double getAmortizacao() {
		return amortizacao;
	}

	public void setAmortizado(double amortizacao) {
		this.amortizacao = amortizacao;
	}

	public double getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(double saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	
}
