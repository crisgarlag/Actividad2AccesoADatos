import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Obtener {
	public static void main(String[] args) {
		// Paso 1: Establecemos los parametros de conexión con la base de datos
		String cadenaConexion = "jdbc:mysql://localhost:3306/bbdd";
		String user = "root";
		String pass = "";
		
		// Paso 2: Interactuar con la BD 
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)){
			PreparedStatement sentencia = con.prepareStatement("SELECT * FROM coches WHERE ID=?");
			sentencia.setInt(1, 6);
			ResultSet rs = sentencia.executeQuery();
			while (rs.next()) {
				System.out.print(rs.getInt("ID"));//DAME EL VALOR DE LA COLUMNA ID
				System.out.print(" - "); 
				
				System.out.print(rs.getString("MATRICULA"));
				System.out.print(" - "); 
				System.out.print(rs.getString("MARCA"));
				System.out.print(" - "); 
				System.out.print(rs.getString("MODELO"));
				System.out.print(" - "); 
				System.out.print(rs.getString("COLOR"));
				System.out.print(" - "); 
				System.out.println(); // Retorno de carro
			}
		} catch (SQLException e) {
			System.out.println("Error al realizar el listado de coches");
			System.out.println(e.getMessage());
		}		
		
	}
}
