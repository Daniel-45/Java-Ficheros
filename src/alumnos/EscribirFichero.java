package alumnos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */
public class EscribirFichero {

	private static final String RUTA = "files/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Set<Alumno> alumnos = new HashSet<Alumno>();

		try (FileWriter bruto = new FileWriter(RUTA + "alumnos.csv");
				BufferedWriter buffer = new BufferedWriter(bruto);
				PrintWriter escritor = new PrintWriter(buffer)) {

			String seguir;

			do {

				Alumno a = new Alumno(Teclado.leerString("\nIntroduce el DNI del alumno:"));

				a.leerDatos();

				if (alumnos.add(a)) {
					System.out.println("Alumno " + a.getNombre() + " insertado correctamente!!");
				} else {
					System.out.println("Alumno " + a.getNombre() + " no se ha podido insertar!!");
				}

				seguir = Teclado.leerString("\nQuieres seguir insertando alumnos? S/N");

			} while (seguir.equalsIgnoreCase("S"));

			for (Alumno a : alumnos) {
				escritor.println(a.toString());
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}