import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidad.coche;
public class AlmacenarEnObjeto {
	public static void main(String[] args) {
		// Paso 1: Establecemos los parametros de conexión con la base de datos
		String cadenaConexion = "jdbc:mysql://localhost:3306/bbdd";
		String user = "root";
		String pass = "";

		// Paso 2: Interactuar con la BD
		try (Connection con = DriverManager.getConnection(cadenaConexion, user, pass)){
			PreparedStatement sentencia = con.prepareStatement("SELECT * FROM PERSONAS");
			ResultSet rs = sentencia.executeQuery();
			
			List<coche> listadoPersonas = new ArrayList<coche>();
			while (rs.next()) {
				coche p = new coche();
				p.setId(rs.getInt("ID"));
				p.setMatricula(rs.getString("MATRICULA"));
				p.setModelo(rs.getString("MODELO"));
				p.setMarca(rs.getString("MARCA"));
				p.setColor(rs.getString("COLOR"));
				
				listadoPersonas.add(p);
			}

			System.out.println(listadoPersonas);
		} catch (SQLException e) {
			System.out.println("Error al realizar el listado de productos");
			System.out.println(e.getMessage());
		}
	}
}
