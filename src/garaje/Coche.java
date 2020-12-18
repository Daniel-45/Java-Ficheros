package garaje;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import utilidades.Libreria;


public class Coche extends Vehiculo {

	private int numeroPlazas;

	public Coche() {
		super();
		this.numeroPlazas = 2;
	}

	public Coche(String matricula, int potencia, int numeroPlazas) {
		super(matricula, potencia);
		setNumeroPlazas(numeroPlazas);
	}

	public Coche(String matricula) {
		super(matricula);
		this.numeroPlazas = 2;
	}

	// Copia
	public Coche(Coche original) {
		super(original.getMatricula(), original.getPotencia());
		this.numeroPlazas = original.numeroPlazas;
	}

	public int getNumeroPlazas() {
		return numeroPlazas;
	}

	public void setNumeroPlazas(int numeroPlazas) {
		if (numeroPlazas <= 0) {
			throw new IllegalArgumentException("\nValor para el número de plazas no permitido!!");
		}
		this.numeroPlazas = numeroPlazas;
	}

	// Leer datos
	public void leerDatos() {
		super.leerDatos();
		setNumeroPlazas(Libreria.leerPositivo("\nIntroduce el número de plazas:"));
	}

	// Leer datos modo binario
	public void leerBinario(DataInputStream fichero) throws IOException {
		super.leerBinario(fichero);

		try {
			setNumeroPlazas(fichero.readInt());
		} catch (IllegalArgumentException e) {
			numeroPlazas = 2;
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		super.escribirBinario(fichero);
		fichero.writeInt(numeroPlazas);
	}

	// Escribir datos modo texto (CSV)
	public String toCSV() {
		return super.toCSV() + ":" + numeroPlazas;
	}

	// Mostrar datos
	@Override
	public String toString() {
		return super.toCSV() + "Coche [Número de plazas=" + numeroPlazas + "]";
	}

	public void mostrarDatos() {
		super.mostrarDatos();
		System.out.println("\nNúmero de plazas: " + numeroPlazas);
	}

	@Override
	public float calculaCuota() {
		return super.getPotencia() * numeroPlazas;
	}
}
