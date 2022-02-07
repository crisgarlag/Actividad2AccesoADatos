package entidad;

import java.util.Scanner;

public class Pasajero {
	static Scanner sc = new Scanner(System.in);
	/**
	 * Declaraci�n de las variables de la entidad
	 */
	private int id;
	private String nombre;
	private int edad;
	private double peso;
	private int idCoche;

	
	/**
	 * Constructores de la clase
	 */
	public Pasajero() {
		super();
	}
	
	public Pasajero(String nombre, int edad, double peso) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
	}
	
	public Pasajero(String nombre, int edad, double peso, int idCoche) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		this.idCoche=idCoche;
	}
	
	/**
	 * GETTERS AND SETTERS
	 */

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int getIdCoche() {
		return idCoche;
	}

	public void setIdCoche(int idCoche) {
		this.idCoche = idCoche;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * M�todo toString
	 */
	
	@Override
	public String toString() {
		return "Pasajero con id:" + id + ", nombre: " + nombre + ", edad: " + edad + ", peso: " + peso + ", idCoche:"
				+ idCoche;
	}

	//private DaoPasajeroMySQL daoPasajero;

	/**
	 * Solicita datos de un pasajero para darlos de alta
	 * @return devuelve un pasajero con los datos pasados por la consola
	 */
	public Pasajero pedirPasajero() {
		
		//Variables
		String nombre;
		String edad;
		String peso;
		
		System.out.println("Escribir el Nombre");
		nombre = sc.nextLine();
		
		System.out.println("Escribir la Edad");
		edad = sc.nextLine();
		
		System.out.println("Escribir el Peso");
		peso = sc.nextLine();
		
		Pasajero pasajero = new Pasajero(nombre, Integer.parseInt(edad), Double.parseDouble(peso));
		return pasajero;

	}

	/**
	 * Da de alta un pasajero en el coche si este contiene menos de 5 pasajeros
	 * @param idPasajero del pasajero a añadir en el coche
	 * @param idCoche del coche
	 * @return true si lo añade y false si no lo hace
	 
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
	*/
}

