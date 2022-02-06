package modelo.persistencia.interfaces;


import java.util.List;

import modelo.entidad.Coche;
/**
 * Interfaz que contiene los metodos para actuar como Dao de un coche
 * @author cristiangarcialagar
 *
 */
public interface DaoCoche{
	/**
	 * @param c coche a insertar
	 * @return true si se realiza el alta y false si no
	 */
	public boolean alta(Coche c);
	/**
	 * Permite dar de baja un coche según su id
	 * @param id del coche a dar de baja
	 * @return true si se realiza la baja y false si no
	 */
	public boolean borrar(int id);
	/**
	 * Permite consultar un coche por id
	 * @param id del coche a consultar
	 * @return el coche si existe y null si no existe
	 */
	public Coche consultar(int id);
	/**
	 * Permite modicar un coche por id
	 * @param id del coche a modificar
	 * @return true si se modifica y false si no lo hace
	 */
	public boolean modificar(int id);
	/**
	 * 
	 * @return una lista con todos los coches
	 */
	public List<Coche> listarCoches(); 

}
