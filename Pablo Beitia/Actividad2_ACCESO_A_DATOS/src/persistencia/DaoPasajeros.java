package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidad.Coche;
import entidad.Pasajero;

public class DaoPasajeros {

		private Connection conexion;
		private Query q = new Query();
		
		
		public DaoPasajeros() {
			q = new Query();
		}
		
		//Método para crear conexión con la base de datos
		
		public boolean crearConexion() {
			String direccion = "jdbc:mysql://localhost:3306/coches";
			String user = "root";
			String pass = "";
			
			try {
				conexion = DriverManager.getConnection(direccion, user, pass);
			} catch (SQLException e) {
				System.out.println("---------------------------------------------------");
				System.out.println("No se ha podido conectar con la base de datos." + e);
				System.out.println("---------------------------------------------------");
				return false;
			}
			return true;
		}
		
		//Método para cerrar la conexión con la base de datos
		
		public boolean finConexion() {
			try {
				conexion.close();
			}catch (SQLException e) {
				System.out.println("---------------------------------");
				System.out.println("No se ha podido cerrar la conexión.");
				System.out.println("---------------------------------");
				return false;
			}
			
			return true;
		}
		
		//Método para añadir un objeto pasajero a la bbdd
		public boolean añadirPasajero() {
			Pasajero pasajero = new Pasajero();
			pasajero.pedirPasajero();
			boolean añadido = false;
			
			//Comprobamos si la consexión se ha creado correctamente
			if(crearConexion() == false) {
				return false;
			}
			
			String insertarPasajero = "INSERT INTO PASAJEROS(NOMBRE,"
															+ "EDAD,"
															+ "PESO "
															+ "VALUES (?,?,?))";
			try {
				PreparedStatement ps = conexion.prepareStatement(insertarPasajero);
				ps.setString(1, pasajero.getNombre());
				ps.setInt(2, pasajero.getEdad());
				ps.setDouble(3, pasajero.getPeso());
				
				int cambios = ps.executeUpdate();
				if (cambios == 0) {
					añadido = false;
				} else {
					System.out.println("---------------------------------------");
					System.out.println("El pasajero se ha añadido correctamente");
					System.out.println("---------------------------------------");
				}
			}catch (Exception e) {
				System.out.println("---------------------------------------");
				System.out.println("ERROR AL AÑADIR PASAJERO: " + e);
				System.out.println("---------------------------------------");
				añadido = false;
			}finally {
				finConexion();
			}
			
			return añadido;
		}
		
		//Método para eliminar pasajeros a través de su id
		public boolean eliminarPasajero(int id) {
			boolean eliminado = false;
			
			
			//Comprobamos si la conexión se ha creado correctamente
			if(crearConexion() == false) {
				return false;
			}
			
			String borrarPasajero = "DELETE FROM PASAJEROS WHERE ID=?";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(borrarPasajero);
				ps.setInt(1, id);
				
				int cambios = ps.executeUpdate();
				if (cambios != 0) {
					eliminado = true;
					System.out.println("---------------------------");
					System.out.println("El pasajero se ha eliminado");
					System.out.println("---------------------------");
				}else {
					System.out.println("----------------------------------------------------");
					System.out.println("No se ha encontrado ningún pasajero con el id: " + id);
					System.out.println("----------------------------------------------------");
				}
			}catch (Exception e) {
				e.printStackTrace();
				eliminado = false;
			} finally {
				finConexion();
			}
			
			return eliminado;
		}
		
		
		//Método para consultar pasajeros según el id
		
		public Pasajero consultarId(int id) {
			Pasajero pasajero = null;
			
			//Comprobamos si la consexión se ha creado correctamente
			if(crearConexion() == false) {
				return pasajero;
			}
			
			String consultar = "SELECT * FROM PASAJEROS WHERE id = ?";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(consultar);
				ps.setInt(1, id);
				
				ResultSet resultadoConsulta = ps.executeQuery();
				if(resultadoConsulta == null) {
					System.out.println("-------------------------------");
					System.out.println("No se ha encontrado el pasajero.");
					System.out.println("-------------------------------");
				}else {
					while (resultadoConsulta.next()) {
						pasajero = q.obtenerPasajero(resultadoConsulta);
					}
				}
				
			}catch (Exception e) {
				System.out.println("-------------------------------");
				System.out.println("Error al buscar pasajero -> " + e);
				System.out.println("-------------------------------");
			}finally {
				finConexion();
			}
			
			return pasajero;
		}
		

		
		// Método que muestra todos los pasajeros que hay en la base de datos
		public ArrayList<Pasajero> listarPasajeros(){
			
			ArrayList<Pasajero> listaPasajeros = null;
			Pasajero añadirALista;
			
			//Comprobamos si la consexión se ha creado correctamente
			if(crearConexion() == false) {
				return listaPasajeros;
			}
			
			String listar = "SELECT * FROM PASAJEROS";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(listar);
				ResultSet resultadoConsulta = ps.executeQuery();
				
				if (resultadoConsulta == null) {
					System.out.println("---------------------------------------------");
					System.out.println("Todavía no hay pasajeros en la Base de datos");
					System.out.println("---------------------------------------------");
				}else {
					listaPasajeros = new ArrayList<Pasajero>();
					while (resultadoConsulta.next()) {
						añadirALista = q.obtenerPasajero(resultadoConsulta);
						listaPasajeros.add(añadirALista);
					}
				}
			}catch (Exception e) {
				System.out.println("La lista de pasajeros está vacía" + e);
				return listaPasajeros;
			}finally {
				finConexion();
			}
			return listaPasajeros;
		}
}