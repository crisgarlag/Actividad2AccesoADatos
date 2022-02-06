package modelo.negocio;

import java.util.Scanner;

import modelo.entidad.Coche;


/**
 * Clase que permite al cliente interactuar con el daoCoche
 * @author cristiangarcialagar
 *
 */
public class GestorCoche {

	/**
	 * Solicita datos de un pasajero para darlos de alta
	 * @return devuelve un coche con los datos pasados por la consola
	 */
	public Coche solicitarCoche() {

		String matricula;
		String marca;
		String modelo;
		String color;
		Scanner sc = new Scanner(System.in);
		System.out.println("Matricula");
		matricula = sc.nextLine();
		System.out.println("Marca");
		marca = sc.nextLine();
		System.out.println("Modelo");
		modelo = sc.nextLine();
		System.out.println("Color");
		color = sc.nextLine();
		Coche coche = new Coche(matricula, marca, modelo, color);
		return coche;

	}
}
