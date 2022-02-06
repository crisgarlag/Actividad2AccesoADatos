package vista;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.negocio.GestorCoche;
import modelo.negocio.GestorPasajero;
import modelo.persistencia.DaoCocheMySQL;
import modelo.persistencia.DaoPasajeroMySQL;

public class Menu {

	private static Scanner sc = new Scanner(System.in);

	/**
	 * Muestra el menu de la aplicacion
	 * 
	 * @param daoCoches
	 */
	public static void menu(DaoCocheMySQL daoCoches, DaoPasajeroMySQL daoPasajero) {

		String opcion = "0";

		while (!opcion.equals("7")) {
			System.out.println("**************************");
			System.out.println("*** Menu Almacen Coche ***");
			System.out.println("**************************");
			System.out.println("Seleccione una de las siguientes opciones.");
			System.out.println("1.- Añadir nuevo coche");
			System.out.println("2.- Borrar coche por id");
			System.out.println("3.- Consulta coche por id");
			System.out.println("4.- Modificar coche por id");
			System.out.println("5.- Listado de coches");
			System.out.println("6.- Gestión Pasajeros");
			System.out.println("7.- Terminar el programa");
			System.out.println("**************************");
			System.out.println("*                        *");
			System.out.println("**************************");

			opcion = sc.nextLine();

			ejecutarOpcionMenu(opcion, daoCoches, daoPasajero);

		}

	}

	/**
	 * Ejecuta la opcion elegida en el menu
	 * 
	 * @param opcion
	 * @param daoCoches
	 */
	public static void ejecutarOpcionMenu(String opcion, DaoCocheMySQL daoCoches, DaoPasajeroMySQL daoPasajero) {
		GestorCoche gestorCoche = new GestorCoche();
		String id;
		switch (opcion) {
		case "1":
			daoCoches.alta(gestorCoche.solicitarCoche());
			break;
		case "2":
			System.out.println("Introduce id del coche a borrar");
			id = sc.nextLine();
			daoCoches.borrar(Integer.parseInt(id));
			break;
		case "3":
			System.out.println("Introduce id del coche a buscar");
			id = sc.nextLine();
			if (daoCoches.consultar(Integer.parseInt(id)) == null)
				System.out.println("El coche no se encuentra en la Base de datos\n");
			else
				System.out.println(daoCoches.consultar(Integer.parseInt(id)));
			break;
		case "4":
			System.out.println("Introduce id del coche a modificar");
			id = sc.nextLine();
			daoCoches.modificar(Integer.parseInt(id));
			break;
		case "5":
			System.out.println("El almacen contiene los siguientes coches:");
			for (Coche c : daoCoches.listarCoches())
				System.out.println(c);
			break;
		case "6":
			Submenu(daoCoches, daoPasajero);
			break;

		}
	}
	/**
	 * Muestra el submenu para operar con los pasajeros
	 * @param daoCoches
	 * @param daoPasajero
	 */
	public static void Submenu(DaoCocheMySQL daoCoches, DaoPasajeroMySQL daoPasajero) {

		String opcion = "0";

		while (!opcion.equals("8")) {
			System.out.println("**************************");
			System.out.println("*** Menu Pasajero Coche ***");
			System.out.println("**************************");
			System.out.println("Seleecione una de las siguientes opciones.");
			System.out.println("1.- Añadir nuevo pasajero");
			System.out.println("2.- Borrar pasajero por id");
			System.out.println("3.- Consulta pasajero por id");
			System.out.println("4.- Listado de pasajeros");
			System.out.println("5.- Añadir pasajero a un coche");
			System.out.println("6.- Borrar pasajero de un coche");
			System.out.println("7.- Listado de pasajeros de un coche");
			System.out.println("8.- Salir de menu pasajero");
			System.out.println("**************************");
			System.out.println("*                        *");
			System.out.println("**************************");

			opcion = sc.nextLine();

			ejecutarOpcionSubMenu(opcion, daoCoches, daoPasajero);

		}

	}
	/**
	 * Ejecuta la opcion elegida en el submenu
	 * @param opcion
	 * @param daoCoches
	 * @param daoPasajero
	 */
	public static void ejecutarOpcionSubMenu(String opcion, DaoCocheMySQL daoCoches, DaoPasajeroMySQL daoPasajero) {
		GestorPasajero gestorPasajero = new GestorPasajero();
		String idPasajero;
		String idCoche;
		switch (opcion) {
		case "1":
			daoPasajero.alta(gestorPasajero.solicitarPasajero());
			break;
		case "2":
			System.out.println("Introduce id del pasajero a borrar");
			idPasajero = sc.nextLine();
			daoPasajero.borrar(Integer.parseInt(idPasajero));
			break;
		case "3":
			System.out.println("Introduce id del pasajero a buscar");
			idPasajero = sc.nextLine();
			if (daoPasajero.consultar(Integer.parseInt(idPasajero)) != null)
				System.out.println(daoPasajero.consultar(Integer.parseInt(idPasajero)));
			else
				System.out.println("No se ha localizado el pasajero\n");
			break;
		case "4":
			System.out.println("La base de datos contiene los siguientes pasajeros:\n");
			for (Pasajero p : daoPasajero.listarPasajeros())
				System.out.println(p);
			break;
		case "5":
			System.out.println("El almacen contiene los siguientes coches disponibles:\n");
			for (Coche c : daoCoches.listarCochesDisponibles()) {
				System.out.println(c);
			}
			System.out.println("\nIntroduce id del pasajero a añadir en el coche");
			idPasajero = sc.nextLine();
			System.out.println("Introduce id del coche");
			idCoche = sc.nextLine();
			gestorPasajero.altaPasajeroEnCoche(Integer.parseInt(idPasajero), Integer.parseInt(idCoche));
			break;
		case "6":
			System.out.println("Estos son los pasajeros en cada coche:\n");
			Map<Coche, Pasajero> cochesPasajeros = daoCoches.listarCochesYPasajeros();
			Iterator iterator = cochesPasajeros.keySet().iterator();
			while (iterator.hasNext()) {
				Coche c = (Coche) iterator.next();
				System.out.println(c + "-->" + cochesPasajeros.get(c));

			}
			System.out.println("\nIntroduce id del coche");
			idCoche = sc.nextLine();
			System.out.println("Introduce id del pasajero a borrar del coche");
			idPasajero = sc.nextLine();
			daoPasajero.borrarPasajeroDeCoche(Integer.parseInt(idPasajero), Integer.parseInt(idCoche));
			break;
		case "7":
			System.out.println("Introduce id del coche");
			idCoche = sc.nextLine();
			System.out.println("Los pasajeros asociados al coche son:");
			for (Pasajero p : daoPasajero.listarPasajerosCoche(Integer.parseInt(idCoche))) {
				System.out.println(p);
			}
			break;
		}
	}

}
