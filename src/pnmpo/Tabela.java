
/**
 * Tabela que mostra as parcelas
 * No sistema original era a tabela do banco de dados 
 */

package pnmpo;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Tabela {

	private JTable tabela;
	
	public Tabela(List<Parcela> parcelas) {
				
		Vector<String> colunas = new Vector<String>();
		colunas.add("Prestação");
		colunas.add("Capital da Parcela");
		colunas.add("Juros");
		colunas.add("Saldo Devedor");
		colunas.add("Data");
		
		// dados é um vetor de vetores!
		Vector<Vector<String>> dados = new Vector<Vector<String>>();
		for (Parcela parcela: parcelas) {
			
			Vector<String> vector = new Vector<String>();
			vector.add(Integer.toString(parcela.getSequencia()));
			vector.add(Double.toString((double) Math.round(100*parcela.getAmortizacao())/100));
			vector.add(Double.toString((double) Math.round(100*parcela.getJuros())/100));
			vector.add(Double.toString((double) Math.round(100*parcela.getSaldoDevedor())/100));
			Calendar cal = Calendar.getInstance();
			cal.setTime(parcela.getData());
			
			String dia = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			if (cal.get(Calendar.DAY_OF_MONTH) < 10)
				dia = "0" + dia;
			String mes = Integer.toString((cal.get(Calendar.MONTH) + 1));
			if ((cal.get(Calendar.MONTH) + 1) < 10)
				mes = "0" + mes;
			String data = dia + "/" + mes + "/" + cal.get(Calendar.YEAR);
			vector.add(data);
			
			dados.add(vector);
		}
		
		tabela = new JTable(dados, colunas);
		tabela.setPreferredScrollableViewportSize(new Dimension(700, 200));
        tabela.setFillsViewportHeight(true);
	}

	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
    	//Create and set up the window.
        JFrame frame = new JFrame("Parcelas");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JPanel newContentPane = new JPanel(new GridLayout(1, 0));

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tabela);
        newContentPane.add(scrollPane);

        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


}
