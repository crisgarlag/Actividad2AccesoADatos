package entidad;

import java.util.Scanner;

public class Coche {
	static Scanner sc = new Scanner(System.in);
	/**
	 * Declaración de las variables de la entidad
	 */
	private int id;
	private String matricula;
	private String marca;
	private String modelo;
	private String color;

	
	/**
	 * Constructores de la clase
	 */
	
	public Coche() {
		super();
	}
	
	public Coche(int id, String matricula, String marca, String modelo, String color) {
		super();
		this.id = id;
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
	}
	

	public Coche(String matricula, String marca, String modelo, String color) {
		super();
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
	}
	
	
	/**
	 * GETTERS AND SETTERS
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Método toString
	 */
	
	@Override
	public String toString() {
		return "El coche tiene id: " + id + ", matricula: " + matricula + ", marca: " + marca + ", modelo: " + modelo + ", color: "
				+ color ;
	}
	
	public Coche pedirCoche() {
		
		//Variables
		String matricula;
		String marca;
		String modelo;
		String color;
		
		
		System.out.println("Escribir la matrícula:");
		matricula = sc.nextLine();
		
		System.out.println("Escribir la marca");
		marca = sc.nextLine();
		
		System.out.println("Escribir el modelo");
		modelo = sc.nextLine();
		
		System.out.println("Escribir el color");
		color = sc.nextLine();
		
		Coche coche = new Coche(matricula, marca, modelo, color);
		
		return coche;
	}
	

	
	
	
}
