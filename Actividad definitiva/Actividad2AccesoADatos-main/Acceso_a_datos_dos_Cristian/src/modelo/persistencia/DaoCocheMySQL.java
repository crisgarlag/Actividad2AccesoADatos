package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.negocio.GestorCoche;
import modelo.negocio.GestorPasajero;
import modelo.persistencia.interfaces.DaoCoche;
/**
 * Implementa la interfaz DaoCoche y contiene la logica para trabajar con el SGBD mysql
 *  @author cristiangarcialagar
 *
 */
public class DaoCocheMySQL implements DaoCoche {

	private Connection conexion;
	private BusquedaEnQuery bq;

	public DaoCocheMySQL() {

		bq = new BusquedaEnQuery();

	}

	/**
	 * Permite abrir la conexion con la bbdd
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

	/**
	 * La matricula del coche no puede repetirse
	 */
	@Override
	public boolean alta(Coche c) {

		boolean alta = true;

		if (!abrirConexion()) {
			return false;
		}

		String queryAlta = "INSERT INTO coches(matricula, marca,modelo,color) VALUES (?,?,?,?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryAlta);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setString(3, c.getModelo());
			ps.setString(4, c.getColor());

			int filas = ps.executeUpdate();
			if (filas == 0) {
				alta = false;
			} else {
				System.out.println("Coche dado de alta correctamente\n");
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("La matricula del coche ya existe\n");
			alta = false;
		} catch (SQLException e) {
			System.out.println("No se ha insertado el coche\n");
			alta = false;
		} finally {
			cerrarConexion();
		}

		return alta;
	}

	/**
	 * No puede borrarse coches que contengan pasajeros
	 */
	@Override
	public boolean borrar(int id) {

		boolean borrar = true;

		if (!abrirConexion()) {
			return false;
		}

		String queryBorrar = "DELETE FROM coches WHERE id=?";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryBorrar);

			ps.setInt(1, id);

			int filas = ps.executeUpdate();
			if (filas == 0) {
				borrar = false;
				System.out.println("El coche no existe en la base de datos\n");
			} else {
				System.out.println("Coche borrado con exito\n");
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("No se puede borrar el coche porque contiene pasajeros\n");
			borrar = false;
		}catch (SQLException e) {
			e.printStackTrace();
			borrar = false;
		} finally {
			cerrarConexion();
		}

		return borrar;
	}

	
	@Override
	public Coche consultar(int id) {

		Coche coche = null;

		if (!abrirConexion()) {
			return coche;
		}

		String query = "SELECT * FROM coches WHERE id = ?";

		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet resultadoQuery = ps.executeQuery();
			if (resultadoQuery != null) {
				while (resultadoQuery.next()) {
					coche = bq.obtenerCocheQuery(resultadoQuery);
				}
			} else {
				System.out.println("No se ha localizado el coche");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return coche;
		} finally {
			cerrarConexion();
		}

		return coche;
	}

	/**
	 * No se puede modificar la matricula si esta ya pertenece a un coche
	 */
	@Override
	public boolean modificar(int id) {
		boolean modificado = true;
		GestorCoche gestoCoche = new GestorCoche();
		Coche coche = gestoCoche.solicitarCoche();

		if (!abrirConexion()) {
			return false;
		}

		String queryModificar = "UPDATE coches SET matricula=?, marca=?, modelo=?, color=? WHERE id=?";

		try {

			PreparedStatement ps = conexion.prepareStatement(queryModificar);
			ps.setString(1, coche.getMatricula());
			ps.setString(2, coche.getMarca());
			ps.setString(3, coche.getModelo());
			ps.setString(4, coche.getColor());
			ps.setInt(5, id);

			int filas = ps.executeUpdate();
			if (filas == 0) {
				modificado = false;
				System.out.println("No se encuentra el coche en la base de datos\n");
			} else {
				System.out.println("Coche modificado con exito\n");
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("La matricula del coche ya existe\n");
			modificado = false;
		}catch (SQLException e) {
			e.printStackTrace();
			modificado = false;
		} finally {
			cerrarConexion();
		}

		return modificado;
	}

	/**
	 * 
	 */
	@Override
	public List<Coche> listarCoches() {

		List<Coche> lista = null;
		Coche coche;

		if (!abrirConexion()) {
			return lista;
		}

		String queryLista = "SELECT * FROM coches";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryLista);
			ResultSet resultadoQuery = ps.executeQuery();
			if (resultadoQuery != null) {
				lista = new ArrayList<Coche>();
				while (resultadoQuery.next()) {
					coche = bq.obtenerCocheQuery(resultadoQuery);
					lista.add(coche);
				}
			} else {
				System.out.println("No se han localizado Coches");
				return lista;
			}

		} catch (SQLException e) {
			System.out.println("No se han podido localizar Coches");
			e.printStackTrace();
			return lista;
		} finally {
			cerrarConexion();
		}

		return lista;
	}

	/**
	 * 
	 * @return una listar con los coches que tienen menos de 5 pasajeros.
	 */
	public List<Coche> listarCochesDisponibles() {

		List<Coche> lista = null;
		Coche coche;

		if (!abrirConexion()) {
			return lista;
		}

		String queryLista = "SELECT c.*, COUNT(p.idCoche) as totalPasajeroCoches FROM coches c LEFT JOIN pasajeros p on c.id=p.idCoche GROUP BY c.id HAVING totalPasajeroCoches<5";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryLista);
			ResultSet resultadoQuery = ps.executeQuery();
			if (resultadoQuery != null) {
				lista = new ArrayList<Coche>();
				while (resultadoQuery.next()) {
					coche = bq.obtenerCocheQuery(resultadoQuery);
					lista.add(coche);
				}
			} else {
				System.out.println("No se han localizado coches disponibles");
				return lista;
			}

		} catch (SQLException e) {
			System.out.println("No se han podido localizar Coches");
			e.printStackTrace();
			return lista;
		} finally {
			cerrarConexion();
		}

		return lista;
	}

	/**
	 * 
	 * @return un map con todos los coches y los pasajeros asociados al mismo
	 */
	public Map<Coche, Pasajero> listarCochesYPasajeros() {

		Map<Coche, Pasajero> lista = null;
		Coche coche;
		Pasajero pasajero;

		if (!abrirConexion()) {
			return lista;
		}

		String queryLista = "SELECT * FROM coches c INNER JOIN pasajeros p on c.id=p.idCoche";

		try {
			PreparedStatement ps = conexion.prepareStatement(queryLista);
			ResultSet resultadoQuery = ps.executeQuery();
			if (resultadoQuery != null) {
				lista = new HashMap<Coche, Pasajero>();
				while (resultadoQuery.next()) {
					coche = bq.obtenerCocheQuery(resultadoQuery);
					pasajero = bq.obtenerPasajeroEnCocheQuery(resultadoQuery);
					lista.put(coche, pasajero);
				}
			} else {
				System.out.println("No se han localizado coches con pasajeros");
				return lista;
			}

		} catch (SQLException e) {
			System.out.println("No se han podido localizar Coches");
			e.printStackTrace();
			return lista;
		} finally {
			cerrarConexion();
		}

		return lista;
	}


}
