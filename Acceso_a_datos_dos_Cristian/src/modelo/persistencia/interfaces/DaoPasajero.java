package modelo.persistencia.interfaces;

import java.util.List;


import modelo.entidad.Pasajero;

/**
 * Interfaz que contiene los metodos para actuar como Dao de un pasajero
 * @author cristiangarcialagar
 *
 */
public interface DaoPasajero {
	
	/**
	 * /**
	 * Permite dar de alta un pasajero pasado como parametro
	 * @param p pasajero a insertar
	 * @return true si se realiza el alta y false si no
	 */
	public boolean alta(Pasajero p);
	/**
	 * Permite dar de baja un pasajero según su id
	 * @param id del pasajero a dar de baja
	 * @return true si se realiza la baja y false si no
	 */
	public boolean borrar(int id);
	/**
	 * Permite consultar un pasajero por id
	 * @param id del pasajero a consultar
	 * @return el pasajero si existe y null si no existe
	 */
	public Pasajero consultar(int id);
	/**
	 * 
	 * @return una lista con todos los pasajeros
	 */
	public List<Pasajero> listarPasajeros(); 
	/**
	 * Permite dar de alta un pasajero en un coche
	 * @param idPasajero del pasajero a añadir en el coche
	 * @param idCoche del coche 
	 * @return true si se da de alta y false si no
	 */
	public boolean altaPasajeroEnCoche(int idPasajero, int idCoche);
	/**
	 * Metodo que borra un pasajeros de un coche
	 * @param idPasajero a borrar del coche
	 * @param idCoche del coche
	 * @return tru si lo borra y false si no
	 */
	public boolean borrarPasajeroDeCoche(int idPasajero, int idCoche);
	/**
	 * Lista de los pasajeros de un coche
	 * @param idCoche del coche que se quieren consultar los pasajeros
	 * @return lista con los pasajeros de un coche
	 */
	public List<Pasajero> listarPasajerosCoche(int idCoche); 

}
