package gr.ftdnascan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FtdnaStore {

	public void store(List<Kit> kits) {
		
		String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
		String USER = "dna";
		String PASS = "password";
	
		Connection conn = null;
		Statement stmt = null;
		   
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("oracle.jdbc.OracleDriver"); // ojdbc6.jar 

		      //STEP 3: Open a connection
		      System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      
		      //STEP 4: Execute a query
		      System.out.println("conn.createStatement()...");
		      stmt = conn.createStatement();
		      
		      System.out.println("Deleting previous records...");
		      String deleteSql = "";
		      deleteSql = "DELETE FROM kit2mutation";
		      System.out.println(deleteSql);
		      stmt.executeUpdate(deleteSql);
		      deleteSql = "DELETE FROM mutation";
		      System.out.println(deleteSql);
		      stmt.executeUpdate(deleteSql);
		      deleteSql = "DELETE FROM kits";
		      System.out.println(deleteSql);
		      stmt.executeUpdate(deleteSql);
		      
		      System.out.println("Inserting records into the table...");
		      for(Kit kit: kits) { 
				String sql = kit.getSqlInserStatement();
				// System.out.println(sql);
				stmt.executeUpdate(sql);
				}
		      
		      System.out.println("Inserted " + kits.size() + " records into the table.");

		   }catch(SQLException se){
		      //Handle errors for JDBC
			  System.out.println( "ERROR: " + se.getMessage() );
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
			  System.out.println( "ERROR: " + e.getMessage() );
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");

		   
	}
}
