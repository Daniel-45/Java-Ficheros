package escalada;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */

public class Clasica extends Via {

	private List<Largo> largos;
	private static final float COSTE = 2000;

	public Clasica() {
		super();
		this.largos = new ArrayList<Largo>();
	}

	public Clasica(String nombre, int grado, int longitud, List<Largo> largos) {
		super(nombre, grado, longitud);
		this.largos = largos;
	}

	public Clasica(String nombre) {
		super(nombre);
		this.largos = new ArrayList<Largo>();
	}

	public Iterator<Largo> getLargos() {
		return largos.iterator();
	}

	public void setLargos(List<Largo> largos) {
		this.largos = largos;
	}

	// Leer datos
	public void leerDatos() {
		super.leerDatos();

		Largo l;
		String seguir;

		do {

			l = new Largo();
			l.leerDatos();
			largos.add(l);

			do {
				seguir = Teclado.leerString("\n¿Quieres seguir? S/N:");
			} while (!seguir.equalsIgnoreCase("S") && !seguir.equalsIgnoreCase("N"));

		} while (seguir.equalsIgnoreCase("S"));
	}

	// Leer datos modo binario
	public void leerBinario(DataInputStream fichero) throws IOException {
		super.leerBinario(fichero);

		Largo l;

		int cuantos = fichero.readInt();

		for (int i = 0; i < cuantos; i++) {
			l = new Largo();
			l.leerBinario(fichero);
			largos.add(l);
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		super.escribirBinario(fichero);

		fichero.writeInt(largos.size());

		for (Largo l : largos) {
			l.escribirBinario(fichero);
		}
	}

	// Escribir datos modo texto (CSV)
	public String toCSV() {
		String CSV = super.toCSV();
		CSV += largos.size();

		for (Largo l : largos) {
			CSV += ":" + l.toCSV();
		}

		return CSV;
	}

	// Mostrar datos
	public void mostrarDatos() {
		super.mostrarDatos();
		for (Largo l : largos) {
			l.mostrarDatos();
		}
	}

	// Calcular rescate
	@SuppressWarnings("unused")
	@Override
	public float calculaRescate() {
		float resultado = 0;
		boolean reunion;

		for (Largo l : largos) {
			if (l.isReunion() == false) {
				resultado = COSTE + (250 * largos.size());
			}
		}

		return resultado;
	}
}
