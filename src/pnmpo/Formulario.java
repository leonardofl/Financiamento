package pnmpo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Formulario extends JFrame {

	private static final long serialVersionUID = 1L; // para não ter warning
	// entradas
	private JTextField valorContrText;
	private JTextField numPrestText;
	private JTextField taxaJurosText;
	private JTextField dtContrText;
	private JTextField diaDebitoText;
	
	// botões
	private JButton botaoSimular;
	private JButton botaoParcelas;
	
	// saídas
	private JLabel IOFLabel;
	private JLabel valPrestLabel;
	private JLabel jurosCarenciaLabel;
	private JLabel totalJurosLabel;

	private JLabel IOFLabelRes;
	private JLabel valPrestLabelRes;
	private JLabel jurosCarenciaLabelRes;
	private JLabel totalJurosLabelRes;

	private Tabela tabela;
	
	public Formulario(String titulo) {
		
		super(titulo);
		
		// 6 linhas, 2 colunas
		getContentPane().setLayout(new GridLayout(11, 2));
	}
	
	public void exibir() {
		
		JLabel label1 = new JLabel("Valor Contratado");		
		valorContrText = new JTextField("");

		JLabel label2 = new JLabel("Número de prestações");		
		numPrestText = new JTextField("");

		JLabel label3 = new JLabel("Taxa de juros (%)");		
		taxaJurosText = new JTextField("");

		JLabel label4 = new JLabel("DT-CONTR (dd/mm/aaaa)");		
		dtContrText = new JTextField("");

		JLabel label5 = new JLabel("Dia para débito");		
		diaDebitoText = new JTextField("");

		IOFLabel = new JLabel("IOF = ");
		valPrestLabel = new JLabel("Valor da prestação = ");
		jurosCarenciaLabel = new JLabel("Juros de carência = ");
		totalJurosLabel = new JLabel("Total de juros = ");

		IOFLabelRes = new JLabel("");
		valPrestLabelRes = new JLabel("");
		jurosCarenciaLabelRes = new JLabel("");
		totalJurosLabelRes = new JLabel("");

		botaoSimular = new JButton("Simular / calcular cronograma");
		botaoSimular.addActionListener(new SimulaListener());

		botaoParcelas = new JButton("Vizualizar parcelas");
		botaoParcelas.addActionListener(new ParcelasListener());

		getContentPane().add(label1);
		getContentPane().add(valorContrText);
		getContentPane().add(label2);
		getContentPane().add(numPrestText);		
		getContentPane().add(label3);
		getContentPane().add(taxaJurosText);
		getContentPane().add(label4);
		getContentPane().add(dtContrText);
		getContentPane().add(label5);
		getContentPane().add(diaDebitoText);
		getContentPane().add(IOFLabel);		
		getContentPane().add(IOFLabelRes);		
		getContentPane().add(valPrestLabel);		
		getContentPane().add(valPrestLabelRes);		
		getContentPane().add(jurosCarenciaLabel);		
		getContentPane().add(jurosCarenciaLabelRes);		
		getContentPane().add(totalJurosLabel);		
		getContentPane().add(totalJurosLabelRes);		
		getContentPane().add(botaoSimular);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);

	}
	
	// rotina para auxiliar testes
	public void preencher() {
		
		valorContrText.setText("2500");
		numPrestText.setText("12");
		taxaJurosText.setText("4");
		dtContrText.setText("12/11/2009");
		diaDebitoText.setText("4");
	}

	
	// listener do botão "simular"
	private class SimulaListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
						
			// variáveis do formulário
			double ValContr = Double.parseDouble(valorContrText.getText());
			double TaxaJuros = Double.parseDouble(taxaJurosText.getText());
			int DiaDebito = Integer.parseInt(diaDebitoText.getText());
			int NumPrest = Integer.parseInt(numPrestText.getText());
			
			int diaDataContr = Integer.parseInt(dtContrText.getText().substring(0, 2));
			int mesDataContr = Integer.parseInt(dtContrText.getText().substring(3, 5));
			int anoDataContr = Integer.parseInt(dtContrText.getText().substring(6, 10));
			Calendar calDataContr = Calendar.getInstance();
			calDataContr.set(anoDataContr, mesDataContr-1, diaDataContr); // "-1" pq janeiro é mês zero!
			Date DataContratacao = calDataContr.getTime();
			
			// variáveis de saída
			double IOF = Calculo.calculaIOF(ValContr);
			double jurosCarencia = Calculo.calculaJurosCarencia(ValContr, TaxaJuros, 
					DataContratacao, DiaDebito);
			double ValPrest = Calculo.calculaPrimeiraPrestacao(ValContr, NumPrest, TaxaJuros, 
					DataContratacao, DiaDebito);

			// cria tabela
			List<Parcela>parcelas = Calculo.calculaTabelaPrice(ValContr, NumPrest, TaxaJuros, DataContratacao, DiaDebito);
			tabela = new Tabela(parcelas);
			double TotalJur = Calculo.getTotalJur();

			// imprime saída
			IOFLabelRes.setText(Double.toString((double) Math.round(IOF*100)/100));
			jurosCarenciaLabelRes.setText(Double.toString((double) Math.round(jurosCarencia*100)/100));
			valPrestLabelRes.setText(Double.toString((double) Math.round(ValPrest*100)/100));
			totalJurosLabelRes.setText(Double.toString((double) Math.round(TotalJur*100)/100));

			// cria botão pra ver tabela
			getContentPane().add(new JLabel()); // gambiarra: label invisível
			getContentPane().add(botaoParcelas);
		}

	}
	
	// listener do botão "vizualiza parcelas"
	private class ParcelasListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			//Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            		            	
	                tabela.createAndShowGUI();
	            }
	        });
		}
		
		
	}
}
