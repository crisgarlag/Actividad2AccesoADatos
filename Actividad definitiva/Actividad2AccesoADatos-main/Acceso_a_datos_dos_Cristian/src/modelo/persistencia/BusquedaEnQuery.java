package modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;

public class BusquedaEnQuery {
	
	/**
	 * Genera un coche con los datos obtenidos en una consulta sql en la que se busca un coche
	 * @param resultadoQuery
	 * @return El coche obtenido en la consulta
	 * @throws SQLException
	 */
	public Coche obtenerCocheQuery(ResultSet resultadoQuery) throws SQLException {
		Coche coche = new Coche();
		coche.setId(resultadoQuery.getInt("id"));
		coche.setMatricula(resultadoQuery.getString("matricula"));
		coche.setMarca(resultadoQuery.getString("marca"));
		coche.setModelo(resultadoQuery.getString("modelo"));
		coche.setColor(resultadoQuery.getString("color"));
		return coche;
	}
	/**
	 * Genera un pasajero con los datos obtenidos en una consulta sql en la que se buscan un pasajeros
	 * @param resultadoQuery
	 * @return El pasajero obtenido en la consulta
	 * @throws SQLException
	 */
	public Pasajero obtenerPasajeroQuery(ResultSet resultadoQuery) throws SQLException {
		Pasajero pasajero = new Pasajero();
		pasajero.setId(resultadoQuery.getInt("id"));
		pasajero.setNombre(resultadoQuery.getString("nombre"));
		pasajero.setEdad(resultadoQuery.getInt("edad"));
		pasajero.setPeso(resultadoQuery.getDouble("peso"));
		pasajero.setIdCoche(resultadoQuery.getInt("idCoche"));
		return pasajero;
	}
	/**
	 * Genera un pasajero con los datos obtenidos en una consulta sql en la que se buscan los pasajeros en un coche
	 * @param resultadoQuery
	 * @return El pasajero obtenido en la consulta
	 * @throws SQLException
	 */
	public Pasajero obtenerPasajeroEnCocheQuery(ResultSet resultadoQuery) throws SQLException {
		Pasajero pasajero = new Pasajero();
		pasajero.setId(resultadoQuery.getInt("p.id"));
		pasajero.setNombre(resultadoQuery.getString("p.nombre"));
		pasajero.setEdad(resultadoQuery.getInt("p.edad"));
		pasajero.setPeso(resultadoQuery.getDouble("p.peso"));
		pasajero.setIdCoche(resultadoQuery.getInt("p.idCoche"));
		return pasajero;
	}


}
