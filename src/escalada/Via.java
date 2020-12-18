package escalada;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Teclado;
import utilidades.Libreria;

/**
 * 
 * @author Daniel
 *
 */

public abstract class Via implements Comparable<Via> {

	private String nombre;
	private int grado, longitud;
	@SuppressWarnings("unused")
	private static final float COSTE = 2000;

	public Via() {
		this.nombre = "";
		this.grado = 0;
		this.longitud = 10;
	}

	public Via(String nombre, int grado, int longitud) {
		this.nombre = nombre;
		setGrado(grado);
		setLongitud(longitud);
	}

	public Via(String nombre) {
		this.nombre = nombre;
		setGrado(grado);
		this.longitud = 10;
	}

	public String getNombre() {
		return nombre;
	}

	public int getGrado() {
		return grado;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setGrado(int grado) {
		if (grado < 0) {
			throw new IllegalArgumentException("\nValor no permitido para el grado de la vía!!");
		}
		this.grado = grado;
	}

	public void setLongitud(int longitud) {
		if (longitud < 10) {
			throw new IllegalArgumentException("\nValor no permitido para la longitud de la vía!!");
		}
		this.longitud = longitud;
	}

	// Leer datos
	public void leerDatos() {
		setGrado(Libreria.leerEntre(0, 10, "\nIntroduce el grado de la vía (0 - 10):"));

		do {

			try {
				longitud = Teclado.leerInt("\nIntroduce la longitud de la vía:");
				setLongitud(longitud);
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR!! La longitud tiene que ser igual o superior a 10 metros\n");
			}

		} while (longitud < 10);
	}

	// Leer datos modo binario
	@SuppressWarnings("deprecation")
	public void leerBinario(DataInputStream fichero) throws IOException {
		nombre = fichero.readLine();

		try {
			setGrado(fichero.readInt());
		} catch (IllegalArgumentException e) {
			grado = 0;
		}

		try {
			setLongitud(fichero.readInt());
		} catch (IllegalArgumentException e) {
			longitud = 10;
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		fichero.writeBytes(nombre + "\n");
		fichero.writeInt(grado);
		fichero.writeInt(longitud);
	}

	// Escribir datos modo texto (CSV)
	public String toCSV() {
		return nombre + ":" + grado + ":" + longitud;
	}

	// Mostrar datos
	@Override
	public String toString() {
		return "vía [Nombre = " + nombre + ", Grado = " + grado + ", Longitud = " + longitud + "]";
	}

	public void mostrarDatos() {
		System.out.println("\nNombre de la vía: " + nombre);
		System.out.println("\nGrado de la vía: " + grado);
		System.out.println("\nLongitud de la vía: " + longitud + " metros");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		boolean igual;
		Via otra = (Via) o;
		if (nombre.equalsIgnoreCase(otra.nombre)) {
			igual = true;
		} else {
			igual = false;
		}
		return igual;
	}

	@Override
	public int compareTo(Via otra) {
		return nombre.compareTo(otra.nombre);
	}

	// Calcular rescate
	public abstract float calculaRescate();
}
