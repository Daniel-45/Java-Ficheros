package alumnos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Daniel
 *
 */
public class AlunmosHtml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Set<Alumno> alumnos = new HashSet<Alumno>();

		try(FileWriter bruto = new FileWriter("resources/static/html/alumnosHTML.html");
				BufferedWriter bufferEscritura = new BufferedWriter(bruto);
				PrintWriter escritor = new PrintWriter(bufferEscritura);
				FileReader lector = new FileReader("files/alumnos.csv");
				BufferedReader bufferLectura = new BufferedReader(lector)){

			// Primera linea del HTML
			escritor.println("<!DOCTYPE html>\n<html>\n<head>\n<link rel=\"stylesheet\" href=\"./../css/index.css\" type=\"text/css\">\n</head>"
					+ "\n<body>\n<header>\n<h1>GRUPO DE ALUMNOS</h1>\n</header>\n<table>\n<tr>\n<th>DNI</th>\n<th>NOMBRE</th>\n<th>NOTA</th>\n<th>FECHA NACIMIENTO</th>\n</tr>");

			while(bufferLectura.ready()) {
				alumnos.add(Alumno.toAlumno(bufferLectura.readLine()));
			}

			for(Alumno a:alumnos)
				escritor.println(a.toHtml()+"\n");

			// Ultima linea del HTML
			escritor.println("</table>\n</body>\n</html>");

		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}
