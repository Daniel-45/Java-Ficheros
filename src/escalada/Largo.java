package escalada;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */

public class Largo {

	private int longitud;
	private boolean reunion;

	public Largo() {
		this.longitud = 10;
		this.reunion = false;
	}

	public Largo(int longitud, boolean reunion) {
		setLongitud(longitud);
		this.reunion = reunion;
	}

	public int getLongitud() {
		return longitud;
	}

	public boolean isReunion() {
		return reunion;
	}

	public void setLongitud(int longitud) {
		if (longitud < 10) {
			throw new IllegalArgumentException("\nERROR!! Valor no permitido para la longitud del largo");
		}
		this.longitud = longitud;
	}

	public void setReunion(boolean reunion) {
		this.reunion = reunion;
	}

	// Leer datos
	public void leerDatos() {
		String tiene;

		do {

			try {
				longitud = Teclado.leerInt("\nIntroduce la longitud del largo:");
				setLongitud(longitud);
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR!! La longitud tiene que ser superior a 10 metros\n");
			}

		} while (longitud < 10);

		do {

			tiene = Teclado.leerString("\n¿Tiene reunión? S/N:");

			if (tiene.equalsIgnoreCase("S")) {

				reunion = true;
			} else {

				reunion = false;
			}

		} while (!tiene.equalsIgnoreCase("S") && !tiene.equalsIgnoreCase("N"));

	}

	// Leer datos modo binario
	public void leerBinario(DataInputStream fichero) throws IOException {

		try {
			longitud = fichero.readInt();
		} catch (IllegalArgumentException e) {
			longitud = 10;
		}

		reunion = fichero.readBoolean();
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		fichero.writeInt(longitud);
		fichero.writeBoolean(reunion);
	}

	// Escribir datos modo texto (CSV)
	public String toCSV() {
		return longitud + ":" + reunion;
	}

	// Mostrar datos
	@Override
	public String toString() {
		return "Largo [Longitud = " + longitud + ", Reunión = " + reunion + "]";
	}

	public void mostrarDatos() {
		System.out.println("\nLongitud el largo: " + longitud + " metros");

		if (reunion == true) {
			System.out.println("\nTiene reunión: Si");
		} else {
			System.out.println("\nTiene reunión: No");
		}
	}
}
