package deportistas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */

public class Ciclista extends Deportista {

	private String nombrePrueba;
	private int etapas, puesto, etapasGanadas;
	private static final long serialVersionUID = 1L;

	public Ciclista() {
		this("");
	}

	public Ciclista(String dni, String nombre, LocalDate fechaN, String nombrePrueba, int etapas, int puesto,
			int etapasGanadas) {
		super(dni, nombre, fechaN);
		this.nombrePrueba = nombrePrueba;
		setEtapas(etapas);
		setPuesto(puesto);
		setEtapasGanadas(etapasGanadas);
	}

	public Ciclista(String dni) {
		super(dni);
		this.etapas = 0;
		this.puesto = 0;
		this.etapasGanadas = 0;
	}

	public String getNombrePrueba() {
		return nombrePrueba;
	}

	public int getEtapas() {
		return etapas;
	}

	public int getPuesto() {
		return puesto;
	}

	public int getEtapasGanadas() {
		return etapasGanadas;
	}

	public void setNombrePrueba(String nombrePrueba) {
		this.nombrePrueba = nombrePrueba;
	}

	public void setEtapas(int etapas) {
		if (etapas <= 0) {
			throw new IllegalArgumentException("\nERROR!! Valor no permitido para el numero de etapas.");
		}
		this.etapas = etapas;
	}

	public void setPuesto(int puesto) {
		if (puesto <= 0) {
			throw new IllegalArgumentException("\nERROR!! Valor no permitido para el puesto en la clasificacion.");
		}
		this.puesto = puesto;
	}

	public void setEtapasGanadas(int etapasGanadas) {
		if (etapasGanadas < 0 || etapas < etapasGanadas) {
			throw new IllegalArgumentException("\nERROR!! Valor no permitido.");
		}
		this.etapasGanadas = etapasGanadas;
	}

	public Ciclista clone() {
		Ciclista copia = new Ciclista(super.getDni(), super.getNombre(), super.getFechaNacimiento(), nombrePrueba,
				etapas, puesto, etapasGanadas);
		return copia;
	}

	// Leer datos
	public void leerDatos() {
		super.leerDatos();
		nombrePrueba = Teclado.leerString("\nIntroduce el nombre de la prueba:");
		setEtapas(Teclado.leerInt("\nIntroduce el numero de etapas:"));
		setPuesto(Teclado.leerInt("\nIntroduce el puesto en la clasificacion:"));
		setEtapasGanadas(Teclado.leerInt("\nNumero de etapas ganadas:"));
	}

	// Mostra datos
	public void mostrarDatos() {
		super.mostrarDatos();
		System.out.println("\nNombre de la prueba: " + nombrePrueba);
		System.out.println("\nPuesto en la clasificacion: " + puesto);
		System.out.println("\nNumero de etapas ganadas: " + etapasGanadas);

	}

	// Leer en modo binario
	@SuppressWarnings("deprecation")
	public void leerDatosBinarios(DataInputStream fichero) throws IOException {
		super.leerDatosBinarios(fichero);
		nombrePrueba = fichero.readLine();

		try {
			setEtapas(fichero.readInt());
		} catch (IllegalArgumentException e) {
			etapas = 0;
		}

		try {
			setPuesto(fichero.readInt());
		} catch (IllegalArgumentException e) {
			puesto = 0;
		}

		try {
			setEtapasGanadas(fichero.readInt());
		} catch (IllegalArgumentException e) {
			etapasGanadas = 0;
		}
	}

	// Escribir en modo binario
	public void escribirDatosBinarios(DataOutputStream fichero) throws IOException {
		super.escribirDatosBinarios(fichero);
		fichero.writeBytes(nombrePrueba + "\n");
		fichero.writeInt(etapas);
		fichero.writeInt(puesto);
		fichero.writeInt(etapasGanadas);
	}

	// Leer en modo texto
	public String fromCSV(String linea) {
		linea = super.fromCSV(linea);
		String[] datos = linea.split("-");
		nombrePrueba = datos[0];

		try {
			setEtapas(Integer.parseInt(datos[1]));
		} catch (IllegalArgumentException e) {
			etapas = 0;
		}

		try {
			setPuesto(Integer.parseInt(datos[2]));
		} catch (IllegalArgumentException e) {
			puesto = 0;
		}

		try {
			setEtapasGanadas(Integer.parseInt(datos[3]));
		} catch (IllegalArgumentException e) {
			etapasGanadas = 0;
		}

		linea = "";

		return linea;
	}

	// Escribir datos en modo texto
	public String toCSV() {
		return super.toCSV() + "-" + nombrePrueba + "-" + etapas + "-" + puesto + "-" + etapasGanadas;
	}
}
