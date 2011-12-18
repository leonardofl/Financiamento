/**
 * 
 * Autor: Leonardo Leite, 28/11/09
 * Conecta programa java a uma base de dados SQLite
 * 
 */

package teste;

import java.sql.*;

public class DBTeste {

	public static void main2(String args[]) {
		
          Connection con = null; //Database objects
          Statement stm = null;
          ResultSet rs = null;
          
          // Testa o driver
          try {
        	  
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver OK");
          } catch (ClassNotFoundException e1) {
				System.out.println("Driver não encontrado");
				e1.printStackTrace();
          }
          
          try {

        	    // conecta
        	  	System.out.println("Conectando...");
	        	con = DriverManager.getConnection("jdbc:sqlite:pnmpo.sqlite");
	        	System.out.println("Conectado!");
	        	
	        	// insere dados
	        	stm = con.createStatement();
	        	stm.executeUpdate("INSERT INTO TabelaPrice (Parcela, Juros) VALUES (2, 0.03)");
	        	stm = con.createStatement();
	        	stm.executeUpdate("INSERT INTO TabelaPrice (Parcela, Juros) VALUES (3, 0.04)"); 
	        	
	        	// consulta
	        	stm = con.createStatement();
	        	rs = stm.executeQuery("SELECT * FROM TabelaPrice");
	        	
	        	while(rs.next()) {
	        		
	        		System.out.println("Parcela: " + rs.getInt("Parcela"));
	        		System.out.println("Juros: " + rs.getDouble("Juros"));
	        	}
    		
          } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
          }
	}
}
