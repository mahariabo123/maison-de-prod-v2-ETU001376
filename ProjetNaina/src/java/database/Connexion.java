
package database;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	  
	public class Connexion {
	  
	    private static Connection con = null;
	  
	   
	    public static Connection getConnection(String url,String user,String mdp)
	    {	 
	        
	       
	        try {
	            Class.forName("org.postgresql.Driver");
	            con = (Connection) DriverManager.getConnection(url,user,mdp);
	           
	            con.setAutoCommit(false);
				System.out.println("nety");
	            return con;
	        }
	        catch (Exception e) {
				System.out.println("ts nety");
	            e.printStackTrace();
	        }
	    
	        return con;
	    }
}
