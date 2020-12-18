package alumnos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Daniel
 *
 */
public class LeerFichero {

	private static final String RUTA = "files/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<Alumno> alumnos = new HashSet<Alumno>();

		try (FileReader bruto = new FileReader(RUTA + "alumnos.csv");
				BufferedReader buffer = new BufferedReader(bruto)) {

			while (buffer.ready()) {
				alumnos.add(Alumno.toAlumno(buffer.readLine()));
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		for (Alumno a : alumnos)
			System.out.println(a.toString());

	}
}
