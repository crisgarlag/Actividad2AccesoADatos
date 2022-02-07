package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidad.Coche;

public class DaoCoches {

		private Connection conexion;
		private Query q = new Query();
		
		
		public DaoCoches() {
			q = new Query();
		}
		
		//M�todo para crear conexi�n con la base de datos
		
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
		
		//M�todo para cerrar la conexi�n con la base de datos
		
		public boolean finConexion() {
			try {
				conexion.close();
			}catch (SQLException e) {
				System.out.println("---------------------------------");
				System.out.println("No se ha podido cerrar la conexi�n.");
				System.out.println("---------------------------------");
				return false;
			}
			
			return true;
		}
		
		//M�todo para a�adir un objeto coche a la bbdd
		public boolean a�adirCoche() {
			Coche coche = new Coche();
			coche.pedirCoche();
			
			boolean a�adido = false;
			
			//Comprobamos si la consexi�n se ha creado correctamente
			if(crearConexion() == false) {
				return false;
			}
			
			String insertarCoche = "INSERT INTO COCHES("
											+ "MATRICULA,"
											+ "MARCA,"
											+ "MODELO"
											+ "COLOR"
											+ "VALUES (?,?,?,?)";
			try {
				PreparedStatement ps = conexion.prepareStatement(insertarCoche);
				ps.setString(1, coche.getMatricula());
				ps.setString(2, coche.getMarca());
				ps.setString(3, coche.getModelo());
				ps.setString(4, coche.getColor());
				
				int cambios = ps.executeUpdate();
				if (cambios == 0) {
					a�adido = false;
				} else {
					System.out.println("---------------------------------------");
					System.out.println("El coche se ha a�adido correctamente");
					System.out.println("---------------------------------------");
				}
			}catch (Exception e) {
				System.out.println("---------------------------------------");
				System.out.println("ERROR AL A�ADIR COCHE: " + e);
				System.out.println("---------------------------------------");
				a�adido = false;
			}finally {
				finConexion();
			}
			
			return a�adido;
		}
		
		//M�todo para eliminar coches a trav�s de su id
		public boolean eliminarCoche(int id) {
			boolean eliminado = false;
			
			
			//Comprobamos si la consexi�n se ha creado correctamente
			if(crearConexion() == false) {
				return false;
			}
			
			String borrarCoche = "DELETE FROM COCHES WHERE ID=?";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(borrarCoche);
				ps.setInt(1, id);
				
				int cambios = ps.executeUpdate();
				if (cambios != 0) {
					eliminado = true;
					System.out.println("---------------------------");
					System.out.println("El coche se ha eliminado");
					System.out.println("---------------------------");
				}else {
					System.out.println("----------------------------------------------------");
					System.out.println("No se ha encontrado ning�n coche con el id: " + id);
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
		
		
		//M�todo para consultar coches seg�n el id
		
		public Coche consultarId(int id) {
			Coche coche = null;
			
			//Comprobamos si la consexi�n se ha creado correctamente
			if(crearConexion() == false) {
				return coche;
			}
			
			String consultar = "SELECT * FROM COCHES WHERE id = ?";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(consultar);
				ps.setInt(1, id);
				
				ResultSet resultadoConsulta = ps.executeQuery();
				if(resultadoConsulta == null) {
					System.out.println("-------------------------------");
					System.out.println("No se ha encontrado el coche.");
					System.out.println("-------------------------------");
				}else {
					while (resultadoConsulta.next()) {
						coche = q.obtenerCoche(resultadoConsulta);
					}
				}
				
			}catch (Exception e) {
				System.out.println("-------------------------------");
				System.out.println("Error al buscar coche -> " + e);
				System.out.println("-------------------------------");
			}finally {
				finConexion();
			}
			
			return coche;
		}
		
		//M�todo para editar los coches seg�n su id
		public boolean editarCoche(int id) {
			boolean editado = true;
			Coche c = new Coche();
			c.pedirCoche();
			
			//Comprobamos si la consexi�n se ha creado correctamente
			if(crearConexion() == false) {
				return false;
			}
			
			String editar = "UPDATE COCHES SET MATRICULA=?,"
											+ "MARCA=?"
											+ "MODELO=?"
											+ "COLOR?"
											+ "WHERE ID = ?";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(editar);
				ps.setString(1, c.getMatricula());
				ps.setString(1, c.getMarca());
				ps.setString(1, c.getModelo());
				ps.setString(1, c.getColor());
				ps.setInt(5, id);
				
				int cambios = ps.executeUpdate();
				
				if(cambios != 0) {
					System.out.println("-------------------------------");
					System.out.println("El coche ha sido editado: " + c);
					System.out.println("-------------------------------");
				}else {
					System.out.println("-------------------------------");
					System.out.println("El coche no ha sido editado.");
					System.out.println("-------------------------------");
					editado = false;
				}
			}catch (Exception e) {
				System.out.println("-------------------------------");
				System.out.println("El coche no ha sido editado.");
				System.out.println("-------------------------------");
			}finally {
				finConexion();
			}
			
			return editado;
		}
		
		// M�todo que muestra todos los coches que hay en la base de datos
		public ArrayList<Coche> listarCoches(){
			
			ArrayList<Coche> listaCoches = null;
			Coche a�adirALista;
			
			//Comprobamos si la consexi�n se ha creado correctamente
			if(crearConexion() == false) {
				return listaCoches;
			}
			
			String listar = "SELECT * FROM COCHES";
			
			try {
				PreparedStatement ps = conexion.prepareStatement(listar);
				ResultSet resultadoConsulta = ps.executeQuery();
				
				if (resultadoConsulta == null) {
					System.out.println("---------------------------------------------");
					System.out.println("Todav�a no hay coches en la Base de datos");
					System.out.println("---------------------------------------------");
				}else {
					listaCoches = new ArrayList<Coche>();
					while (resultadoConsulta.next()) {
						a�adirALista = q.obtenerCoche(resultadoConsulta);
						listaCoches.add(a�adirALista);
					}
				}
			}catch (Exception e) {
				System.out.println("La lista de coches est� vac�a" + e);
				return listaCoches;
			}finally {
				finConexion();
			}
			return listaCoches;
		}
}
