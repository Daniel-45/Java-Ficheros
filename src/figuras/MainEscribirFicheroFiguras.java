package figuras;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */

public class MainEscribirFicheroFiguras {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int tipo;
		
		Figura figura = null;
		
		List<Figura> figuras = new ArrayList<>();

		do {

			try {
				tipo = Teclado.leerInt("\n1.Círculo "
						+ "\n\n2.Cuadrado "
						+ "\n\n3.Triángulo "
						+ "\n\n4.Rectángulo "
						+ "\n\nSelecciona una opción:");
				
				switch (tipo) {
				case 1:
					figura = new Circulo();
					break;
				case 2:
					figura = new Cuadrado();
					break;
				case 3:
					 figura = new Triangulo();
					 break;
				case 4:
					 figura = new Rectangulo();
					 break;
				default:
					System.out.println("\nERROR!! Selecciona una opción válida (1 - 4)\n");
					break;
				}

				figura.leerDatos();
				
				figuras.add(figura);
			}
			catch (NullPointerException e) {
				System.out.println("ERROR!! NullPointerException");
			}
			
		} while (Teclado.leerString("\n¿Quieres seguir insertando figuras? S/N:").equalsIgnoreCase("S"));

		try (FileWriter fichero = new FileWriter("files/figuras.csv");
				BufferedWriter buffer = new BufferedWriter(fichero);
				PrintWriter escritor = new PrintWriter(buffer);) {

			for (Figura f : figuras) {
				escritor.println(f.getClass().getSimpleName() + ":" + f.toCsv());
			}
		}
		catch (IOException e) {
			System.out.println("\nERROR!! Se ha producido un error de acceso al fichero.");
		}
	}

}
