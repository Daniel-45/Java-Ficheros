package alumnos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import daw.com.Teclado;

public class Alumno implements Comparable<Alumno> {

	private String dni;
	private String nombre;
	private float nota;
	private LocalDate fechaN;

	public Alumno() {
		this("", "", 0, null);
	}

	public Alumno(String dni, String nombre, float nota, LocalDate fechaN) {
		this.dni = dni;
		this.nombre = nombre;
		this.nota = nota;
		this.fechaN = fechaN;
	}

	public Alumno(String dni) {
		this(dni, "", 0, null);
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public float getNota() {
		return nota;
	}

	public LocalDate getfechaN() {
		return fechaN;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNota(float nota) {
		if (nota < 1 || nota > 10) {
			throw (new IllegalArgumentException("\nLa nota introducida no es válida!!"));
		}
		this.nota = nota;
	}

	public void setfechaN(LocalDate fechaN) {
		LocalDate fechaMinima = LocalDate.now().minusYears(18);
		if (fechaN != null && fechaMinima.isBefore(fechaN)) {
			throw (new IllegalArgumentException("\nTiene menos de 18 años!!"));
		}
		this.fechaN = fechaN;
	}

	public Alumno clone() {
		return new Alumno(dni, nombre, nota, fechaN);
	}

	// Leer datos alumno
	public void leerDatos() {

		nombre = Teclado.leerString("\nNombre del alumno:");

		// Fecha de nacimiento
		String f;
		DateTimeFormatter formato;
		formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		do {
			try {
				f = Teclado.leerString("\nIntroduce la fecha de nacimiento (dd-mm-yyyy):");
				fechaN = LocalDate.parse(f, formato);
				setfechaN(fechaN);
			} catch (DateTimeParseException | IllegalArgumentException e) {
				System.out.println("\nLa fecha introducida no es valida!!");
				System.out.println("\n" + e.getMessage());
				fechaN = null;
			}
		} while (fechaN == null);

		leerNota();
	}

	// Leer nota alumno
	public void leerNota() {

		boolean valida = false;

		do {
			try {
				nota = Teclado.leerInt("\nNota del alumno:");
				setNota(nota);
				valida = true;
			} catch (IllegalArgumentException e) {
				e.getMessage();
			}
		} while (!valida);
	}

	// Mostrar datos alumno
	public void mostrarDatos() {
		System.out.println("\nDNI: " + dni);
		System.out.println("\nNombre: " + nombre);
		System.out.println("\nNota: " + nota);
		if (fechaN != null) {
			System.out.println("\nFecha de nacimiento: " + fechaN);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	public boolean equals(Object o) {
		boolean igual = false;
		Alumno otro = (Alumno) o;
		if (dni.equals(otro.dni)) {
			igual = true;
		}
		return igual;
	}

	@Override
	public String toString() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return dni + ":" + nombre + ":" + nota + ":" + fechaN.format(formato);
	}

	public static Alumno toAlumno(String csvLinea) {

		String cadena[] = csvLinea.split(":");

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		return new Alumno(cadena[0], cadena[1], Float.parseFloat(cadena[2]), LocalDate.parse(cadena[3], formato));
	}

	// Metodo (toHtml)
	public String toHtml() {

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return "<tr>\n<td>" + dni + "</td>\n<td>" + nombre + "</td>\n<td>" + nota + "</td>\n<td>"
				+ fechaN.format(formato) + "</td>\n</tr>";

	}

	// Metodo (compareTo)
	@Override
	public int compareTo(Alumno otro) {
		return this.dni.compareTo(otro.dni);
	}
}
