package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Pasajero;
import modelo.persistencia.interfaces.DaoPasajero;

/**
 * Implementa la interfaz DaoPasajero y contiene la logica para trabajar con el SGBD mysql
 *  @author cristiangarcialagar
 *
 */
public class DaoPasajeroMySQL implements DaoPasajero {

	private Connection conexion;
	private BusquedaEnQuery bq;

	public DaoPasajeroMySQL() {
		bq = new BusquedaEnQuery();
	}

	/**
	 * Permite abrir la conexion con la bbdd
	 * 
	 * @return true si se conecta y false si no lo hace
	 */
	public boolean abrirConexion() {

		String url = "jdbc:mysql://localhost:3306/COCHES";
		String user = "root";
		String pass = "root";

		try {
			conexion = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("La conexión ha fallado");
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * Permite cerrar la conexion con la bbdd
	 * 
	 * @return true si la cierra y false si no lo hace
	 */
	public boolean cerrarConexion() {

		try {
			conexion.close();
		} catch (SQLException e) {
			System.out.println("El cierre de la conexión ha fallado");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean alta(Pasajero p) {

		boolean alta = true;

		if (!abrirConexion()) {
			return false;
		}

		String queryAlta = "INSERT INTO pasajeros(nombre,edad,peso) VALUES (?,?,?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryAlta);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setDouble(3, p.getPeso());

			int filas = ps.executeUpdate();

			if (filas == 0) {
				alta = false;
			} else {
				System.out.println("Pasajero dado de alta correctamente\n");
			}

		} catch (SQLException e) {
			System.out.println("No se ha insertado el pasajero\n");
			alta = false;
		} finally {
			cerrarConexion();
		}

		return alta;
	}

	@Override
	public boolean borrar(int id) {

		boolean borrado = true;

		if (!abrirConexion()) {
			return false;
		}
		String queryBorrar = "DELETE FROM pasajeros WHERE id=?";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryBorrar);
			ps.setInt(1, id);
			int filas = ps.executeUpdate();

			if (filas == 0) {
				borrado = false;
				System.out.println("No se encuentra el pasajero\n");
			} else {
				System.out.println("Pasajero borrado\n");
			}

		} catch (SQLException e) {
			System.out.println("No se ha borrado el pasajero\n");
			borrado = false;
			e.printStackTrace();
		} finally {
			cerrarConexion();

		}

		return borrado;
	}

	@Override
	public Pasajero consultar(int id) {
		Pasajero pasajero = null;

		if (!abrirConexion()) {
			return null;
		}

		String query = "SELECT * FROM pasajeros WHERE id=?";

		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet resultadoQuery = ps.executeQuery();
			if (resultadoQuery != null) {
				while (resultadoQuery.next()) {
					pasajero = bq.obtenerPasajeroQuery(resultadoQuery);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			cerrarConexion();
		}

		return pasajero;
	}

	@Override
	public List<Pasajero> listarPasajeros() {
		List<Pasajero> lista;

		if (!abrirConexion()) {
			return null;
		}

		String query = "SELECT * FROM pasajeros";

		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ResultSet resultadoQuery = ps.executeQuery();
			lista = new ArrayList<Pasajero>();
			if (resultadoQuery != null) {
				while (resultadoQuery.next()) {
					Pasajero pasajero = bq.obtenerPasajeroQuery(resultadoQuery);
					lista.add(pasajero);
				}
			} else {
				System.out.println("No se han localizado pasajeros");
				return lista;
			}
		} catch (SQLException e) {
			System.out.println("No se ha podido localizar pasajeros");
			e.printStackTrace();
			return null;
		} finally {
			cerrarConexion();
		}

		return lista;
	}

	
	@Override
	public boolean altaPasajeroEnCoche(int idPasajero, int idCoche) {
		boolean alta = true;

		if (!abrirConexion()) {
			return false;
		}

		String queryAlta = "UPDATE pasajeros SET idCoche=? WHERE id=?";

		try {
			PreparedStatement psAlta = conexion.prepareStatement(queryAlta);
			psAlta.setInt(1, idCoche);
			psAlta.setInt(2, idPasajero);
			int fila = psAlta.executeUpdate();

			if (fila == 0) {
				System.out.println("No existe pasajero con ese id\n");
				alta = false;
			}else {
				System.out.println("El pasajero ha sido añadido al coche\n");
			}
		} catch (SQLException e) {
			System.out.println("El id indicado no pertenece a ningun coche\n");
			alta = false;
		} finally {
			cerrarConexion();
		}

		return alta;
	}

	
	@Override
	public boolean borrarPasajeroDeCoche(int idPasajero, int idCoche) {

		boolean borrar = true;

		if (!abrirConexion()) {
			return false;
		}

		String queryBorrar = "UPDATE pasajeros SET idCoche=null WHERE id=? AND idCoche=?";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryBorrar);
			ps.setInt(1, idPasajero);
			ps.setInt(2, idCoche);
			int filas = ps.executeUpdate();
			if (filas == 0) {
				borrar = false;
				System.out.println("No existe pasajero para el coche indicado\n");
			} else {
				System.out.println("Pasajero eliminado del coche\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			borrar = false;
		} finally {
			cerrarConexion();
		}

		return borrar;
	}

	
	@Override
	public List<Pasajero> listarPasajerosCoche(int idCoche) {
		List<Pasajero> lista;

		if (!abrirConexion()) {
			return null;
		}

		String query = "SELECT * FROM pasajeros WHERE idCoche=?";

		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idCoche);
			ResultSet resultadoQuery = ps.executeQuery();
			lista = new ArrayList<Pasajero>();
			while (resultadoQuery.next()) {
				Pasajero pasajero = bq.obtenerPasajeroQuery(resultadoQuery);
				lista.add(pasajero);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			cerrarConexion();
		}

		return lista;
	}

}
