import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarPreparedStatement {
	public static void main(String[] args) {
		// Paso 1: Establecemos los parametros de conexión con la base de datos
		String cadenaConexion = "jdbc:mysql://localhost:3306/bbdd";
		String user = "root";
		String pass = "";
		
		// Paso 2: Interactuar con la BD 
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)){
			//esta es la manera que hay que hacer si quereis aprobar
			//porque? De momento ganamos en claridad, es mas visual
			String sql = "INSERT INTO COCHE (MATRICULA, MODELO, MARCA, COLOR, ID) VALUES (?, ?, ?)"; 
			// en vez de poner los valores ponemos interrogantes
			
			String matricula = "1567hcj";
			String modelo = "Cherroky";
			String marca = "Jeep";
			String color = "Rojo";
			int id = 04;
			
			System.out.println("Se va a ejecutar la siguiente sentencia SQL:");
			System.out.println(sql);
			
			//Notese que usamos PreparedStatement en vez de Statement
			PreparedStatement sentencia;
			sentencia = con.prepareStatement(sql);
			//por un lado mandamos la sql, y por otro mandamos los parametros
			//que la bbdd tiene que sustituir por las "?"
			sentencia.setString(1, matricula);//decimos que la primera "?" que se encuentre le ponga el nombre
			sentencia.setString(2, modelo);
			sentencia.setString(3, marca);
			sentencia.setString(4, color);
			sentencia.setInt(5, id);
			
			
			//Ejecutamos la query
			int afectados = sentencia.executeUpdate();
			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: "+afectados);
		} catch (SQLException e) {
			System.out.println("Error al añadir una nueva persona");
			System.out.println(e.getMessage());
		}
	}
}
