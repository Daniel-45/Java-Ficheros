package garaje;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Teclado;

public class Moto extends Vehiculo {

	private boolean antiRobo;

	public Moto() {
		super();
		this.antiRobo = false;
	}

	public Moto(String matricula, int potencia, boolean antiRobo) {
		super(matricula, potencia);
		this.antiRobo = antiRobo;
	}

	public Moto(String matricula) {
		super(matricula);
		this.antiRobo = false;
	}

	public Moto(Moto original) {
		super(original.getMatricula(), original.getPotencia());
		this.antiRobo = original.antiRobo;
	}

	public boolean isAntiRobo() {
		return antiRobo;
	}

	public void setAntiRobo(boolean antiRobo) {
		this.antiRobo = antiRobo;
	}

	public void leerDatos() {
		super.leerDatos();

		String respuesta = "";

		do {
			respuesta = Teclado.leerString("\n¿Tiene antirobo? S/N:");
			if (respuesta.equalsIgnoreCase("S")) {
				antiRobo = true;
			} else {
				antiRobo = false;
			}
		} while (!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));
	}

	// Leer datos modo binario
	public void leerBinario(DataInputStream fichero) throws IOException {
		super.leerBinario(fichero);
		antiRobo = fichero.readBoolean();
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		super.escribirBinario(fichero);
		fichero.writeBoolean(antiRobo);
	}

	// Escribir datos modo texto (CSV)
	public String toCSV() {
		return super.toCSV() + ":" + antiRobo;
	}

	// Mostrar datos
	@Override
	public String toString() {
		return super.toString() + "Moto [Antirobo = " + antiRobo + "]";
	}

	public void mostrarDatos() {
		super.mostrarDatos();

		if (antiRobo == true) {
			System.out.println("\nAntirobo: Si");
		} else {
			System.out.println("\nAntirobo: No");
		}
	}

	@Override
	public float calculaCuota() {
		return super.getPotencia() * 2;
	}
}
