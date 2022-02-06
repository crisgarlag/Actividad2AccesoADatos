package vista;
import modelo.persistencia.DaoCocheMySQL;
import modelo.persistencia.DaoPasajeroMySQL;

public class Main {

	public static void main(String[] args) {
		
		DaoCocheMySQL daocoche = new DaoCocheMySQL();
		DaoPasajeroMySQL daoPasajero = new DaoPasajeroMySQL();
		Menu.menu(daocoche, daoPasajero);
		System.out.println("Fin del programa");
	}

}
