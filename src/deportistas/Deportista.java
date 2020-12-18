package deportistas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */

public class Deportista implements Comparable<Deportista>, Serializable {

	private String dni, nombre;
	private LocalDate fechaNacimiento;
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter FORMATOFECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public Deportista() {
		this("");
	}

	public Deportista(String dni, String nombre, LocalDate fechaNacimiento) {
		this.dni = dni;
		this.nombre = nombre;
		setfechaNacimiento(fechaNacimiento);
	}

	public Deportista(String dni) {
		this.dni = dni;
		this.nombre = "";
		this.fechaNacimiento = null;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setfechaNacimiento(LocalDate fechaNacimiento) {

		LocalDate edadMinima = LocalDate.now().minusYears(18);

		if (fechaNacimiento == null) {
			throw (new IllegalArgumentException("\nERROR!! Fecha sin datos."));
		}

		if (edadMinima.isBefore(fechaNacimiento)) {
			throw (new IllegalArgumentException("\nERROR!! Deportista menor de edad."));
		}
		this.fechaNacimiento = fechaNacimiento;
	}

	public Deportista clone() {
		Deportista copia = new Deportista(dni, nombre, fechaNacimiento);
		return copia;
	}

	// Leer fecha
	public void leerFecha() {

		String fechaTexto;
		boolean fechaCorrecta;

		do {

			try {
				fechaCorrecta = true;
				fechaTexto = Teclado.leerString("\nIntroduce la fecha de nacimiento:");
				setfechaNacimiento(LocalDate.parse(fechaTexto, FORMATOFECHA));
			} catch (DateTimeParseException e) {
				System.out.println("\nERROR!! No se puede analizar este formato de fecha.");
				fechaCorrecta = false;
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR!! Deportista menor de edad.");
				fechaCorrecta = false;
			}

		} while (!fechaCorrecta);

	}

	// Leer datos
	public void leerDatos() {
		nombre = Teclado.leerString("\nIntroduce el nombre del deportista:");
		leerFecha();
	}

	// Mostrar datos
	public void mostrarDatos() {
		System.out.println("\nDNI: " + dni);
		System.out.println("\nNombre: " + nombre);
		System.out.println("\nFecha Nacimiento: " + fechaNacimiento);
	}

	// Leer modo binario
	@SuppressWarnings("deprecation")
	public void leerDatosBinarios(DataInputStream fichero) throws IOException {

		try {
			dni = fichero.readLine();
			nombre = fichero.readLine();
			setfechaNacimiento(LocalDate.parse(fichero.readLine()));
		} catch (IllegalArgumentException | DateTimeParseException e) {
			fechaNacimiento = null;
		}

	}

	// Escribir modo binario
	public void escribirDatosBinarios(DataOutputStream fichero) throws IOException {
		fichero.writeBytes(dni + "\n");
		fichero.writeBytes(nombre + "\n");
		fichero.writeBytes(fechaNacimiento + "\n");
	}

	// Leer modo texto
	public String fromCSV(String linea) {
		String datos[] = linea.split("-");
		dni = datos[0];
		nombre = datos[1];

		try {
			setfechaNacimiento(LocalDate.parse(datos[2], FORMATOFECHA));
		} catch (IllegalArgumentException | DateTimeParseException e) {
			fechaNacimiento = null;
		}

		for (int i = 0; i < datos.length; i++) {
			linea = linea.substring(linea.indexOf("-") + 1);
		}

		return linea;
	}

	// Escribir modo texto
	public String toCSV() {
		return dni + "-" + nombre + "-" + fechaNacimiento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		boolean igual = false;
		Deportista otro = (Deportista) o;

		if (dni.equals(otro.dni)) {
			igual = true;
		}
		return igual;
	}

	@Override
	public int compareTo(Deportista otro) {
		return dni.compareTo(otro.dni);
	}

}
