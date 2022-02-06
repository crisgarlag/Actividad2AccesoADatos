package modelo.negocio;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import modelo.entidad.Pasajero;
import modelo.persistencia.DaoPasajeroMySQL;

/**
 * Clase que permite al cliente interactuar con el daoPasajero
 * @author cristiangarcialagar
 *
 */
public class GestorPasajero {

	private DaoPasajeroMySQL daoPasajero;

	/**
	 * Solicita datos de un pasajero para darlos de alta
	 * @return devuelve un pasajero con los datos pasados por la consola
	 */
	public Pasajero solicitarPasajero() {
		String nombre;
		String edad;
		String peso;
		Scanner sc = new Scanner(System.in);
		System.out.println("Nombre");
		nombre = sc.nextLine();
		System.out.println("Edad");
		edad = sc.nextLine();
		System.out.println("Peso");
		peso = sc.nextLine();
		Pasajero pasajero = new Pasajero(nombre, Integer.parseInt(edad), Double.parseDouble(peso));
		return pasajero;

	}

	/**
	 * Da de alta un pasajero en el coche si este contiene menos de 5 pasajeros
	 * @param idPasajero del pasajero a añadir en el coche
	 * @param idCoche del coche
	 * @return true si lo añade y false si no lo hace
	 */
	public boolean altaPasajeroEnCoche(int idPasajero, int idCoche) {
		Connection conexion = null;
		daoPasajero = new DaoPasajeroMySQL();

		String url = "jdbc:mysql://localhost:3306/COCHES";
		String user = "root";
		String pass = "root";
		// Se abre la conexion
		try {
			conexion = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("La conexión ha fallado");
			e.printStackTrace();
			return false;
		}
		// Se busca en numero de coches por ID eb la tabla pasajeros para poder saber
		// si hay mas 5 pasajeros ya en el cohe
		String queryLimite = "SELECT COUNT(idCoche) FROM pasajeros WHERE idCoche=? GROUP BY idCoche";

		try {
			PreparedStatement psLimite = conexion.prepareStatement(queryLimite);
			psLimite.setInt(1, idCoche);
			ResultSet resultadoLimite = psLimite.executeQuery();

			while (resultadoLimite.next()) {
				if (resultadoLimite.getInt("COUNT(idCoche)") >= 5) {
					System.out.println("No puede haber mas de 5 pasajeros en el coche");
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			// Se cierra la conexion
		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				System.out.println("El cierre de la conexión ha fallado");
				e.printStackTrace();

			}
		}

		// Si hay menos de 5 pasajeros en el coche, se añade el pasajero
		daoPasajero.altaPasajeroEnCoche(idPasajero, idCoche);
		return true;

	}

}
