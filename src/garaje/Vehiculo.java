package garaje;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import utilidades.Libreria;

public abstract class Vehiculo implements Comparable<Vehiculo> {

	private String matricula;
	private int potencia;

	public Vehiculo() {
		this.matricula = "";
		this.potencia = 1;
	}

	public Vehiculo(String matricula, int potencia) {
		this.matricula = matricula;
		setPotencia(potencia);
	}

	public Vehiculo(String matricula) {
		this.matricula = matricula;
		this.potencia = 1;
	}

	// Copia
	public Vehiculo(Vehiculo original) {
		this.matricula = original.matricula;
		this.potencia = original.potencia;
	}

	public String getMatricula() {
		return matricula;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setPotencia(int potencia) {
		if (potencia <= 0) {
			throw new IllegalArgumentException("\nValor para la potencia no permitido!!");
		}
		this.potencia = potencia;
	}

	// Leer datos
	public void leerDatos() {
		setPotencia(Libreria.leerPositivo("\nIntroduce la potencia:"));
	}

	// Leer datos modo binario
	@SuppressWarnings("deprecation")
	public void leerBinario(DataInputStream fichero) throws IOException {
		matricula = fichero.readLine();

		try {
			setPotencia(fichero.readInt());
		} catch (IllegalArgumentException e) {
			potencia = 1;
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		fichero.writeBytes(matricula + "\n");
		fichero.writeInt(potencia);
	}

	// Escribir datos modo texto (CSV)
	public String toCSV() {
		return matricula + ":" + potencia;
	}

	// Mostrar datos
	@Override
	public String toString() {
		return "Vehiculo [Matricula = " + matricula + ", Potencia = " + potencia + "]";
	}

	public void mostrarDatos() {
		System.out.println("\nMatricula: " + matricula);
		System.out.println("\nPotencia: " + potencia + "CV");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean equals(Object o) {
		boolean igual = false;
		Vehiculo otro = (Vehiculo) o;
		if (matricula.equalsIgnoreCase(otro.matricula)) {
			igual = true;
		}
		return true;
	}

	@Override
	public int compareTo(Vehiculo otro) {
		return matricula.compareTo(otro.matricula);
	}

	public abstract float calculaCuota();
}
