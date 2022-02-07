package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

import entidad.Coche;
import entidad.Pasajero;


public class Query {

	public Coche obtenerCoche(ResultSet resultadoConsulta) throws SQLException {
		Coche coche = new Coche();
		coche.setId(resultadoConsulta.getInt("id"));
		coche.setMatricula(resultadoConsulta.getString("matricula"));
		coche.setMarca(resultadoConsulta.getString("marca"));
		coche.setModelo(resultadoConsulta.getString("modelo"));
		coche.setColor(resultadoConsulta.getString("color"));
		return coche;
	}

	public Pasajero obtenerPasajero(ResultSet resultadoConsulta) throws SQLException {
		Pasajero pasajero = new Pasajero();
		
		pasajero.setId(resultadoConsulta.getInt("id"));
		pasajero.setNombre(resultadoConsulta.getString("nombre"));
		pasajero.setEdad(resultadoConsulta.getInt("edad"));
		pasajero.setPeso(resultadoConsulta.getDouble("peso"));
		pasajero.setIdCoche(resultadoConsulta.getInt("idCoche"));
		
		return pasajero;
	}

}
