package main;

import java.util.Scanner;

import persistencia.DaoCoches;
import persistencia.DaoPasajeros;

public class Main {
	
	static Scanner leer = new Scanner(System.in);
	
	public static void main(String[] args) {
		seleccionCoche();				
	}
	
	public static void seleccionCoche() {
		//Variable de selecci�n
		int seleccion = 0;
		
		DaoCoches daoCoche = new DaoCoches();	
		do {
			menuPrincipal();
			System.out.println("-------------------------------");
			System.out.println("Seleccione un n�mero de opci�n:");
			seleccion = leer.nextInt();
			
			switch (seleccion) {
			case 1: 
				daoCoche.a�adirCoche();
				break;
				
			case 2: 
				System.out.println("Escriba el id del coche a eliminar: ");
				daoCoche.eliminarCoche(leer.nextInt());
				break;
				
			case 3: 
				System.out.println("Escriba el id del coche a editar:");
				System.out.println(daoCoche.editarCoche(leer.nextInt()));
				break;
				
			case 4: 
				System.out.println("Escriba el id del coche a consultar:");
				System.out.println(daoCoche.consultarId(leer.nextInt()));
				break;
				
			case 5: 
				System.out.println("La lista de coches es: ");
				for(entidad.Coche coche : daoCoche.listarCoches()) {
					System.out.println(coche);
				}
				break;
				
			case 6: 
				seleccionPasajero();
				break;
			case 7: 
				seleccion = 8;
				break;
				
			default:
				throw new IllegalArgumentException("Valor no v�lido " + seleccion + ", pruebe con n�meros del 1 al 7.");
			}
		}while(seleccion > 0 && seleccion < 8);
	}
	
	public static void seleccionPasajero() {
		//Variable de selecci�n
		int seleccion = 0;
		
		DaoPasajeros daoPasajeros = new DaoPasajeros();	
		do {
			menuPasajeros();
			System.out.println("-------------------------------");
			System.out.println("Seleccione un n�mero de opci�n:");
			seleccion = leer.nextInt();
			
			switch (seleccion) {
			case 1: 
				daoPasajeros.a�adirPasajero();
				break;
				
			case 2: 
				System.out.println("Escriba el id del pasajero a eliminar: ");
				daoPasajeros.eliminarPasajero(leer.nextInt());
				break;
				
			case 3: 
				System.out.println("Escriba el id del pasajero a consultar:");
				System.out.println(daoPasajeros.consultarId(leer.nextInt()));
				break;
				
			case 4: 
				System.out.println("La lista de pasajeros es: ");
				for(entidad.Pasajero pasajero : daoPasajeros.listarPasajeros()) {
					System.out.println(pasajero);
				}
				break;
				
			case 5: 
				seleccion = 6;
				break;
			default:
				throw new IllegalArgumentException("Valor no v�lido " + seleccion + ", pruebe con n�meros del 1 al 7.");
			}
		}while(seleccion > 0 && seleccion < 6);
	}
	
	public static void menuPrincipal() {
		System.out.println("--------------------------------------");
		System.out.println("------------MEN� DE COCHES------------");
		System.out.println("--------------------------------------");
		System.out.println("1-> A�adir Coche");
		System.out.println("2-> Borrar Coche");
		System.out.println("3-> Modificar Coche");
		System.out.println("4-> Consultar Coche");
		System.out.println("5-> Listar los Coches");
		System.out.println("6-> Men� Pasajeros");
		System.out.println("7-> FIN DEL PROGRAMA");
		System.out.println("--------------------------------------");
		
	}
	
	public static void menuPasajeros() {
		System.out.println("--------------------------------------");
		System.out.println("------------MEN� DE PASAJEROS------------");
		System.out.println("--------------------------------------");
		System.out.println("1-> A�adir Pasajero");
		System.out.println("2-> Borrar Pasajero");
		System.out.println("3-> Consultar Pasajero");
		System.out.println("4-> Listar los Pasajeros");
		System.out.println("5-> Men� Coches");
		System.out.println("--------------------------------------");
		
	}

}
