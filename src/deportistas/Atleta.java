package deportistas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import daw.com.Teclado;

/**
 * 
 * @author Daniel
 *
 */

public class Atleta extends Deportista {

	private String lugar;
	private int distancia;
	private LocalTime marca;
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter FORMATOHORA = DateTimeFormatter.ofPattern("HH:mm:ss");

	public Atleta() {
		this("");
	}

	public Atleta(String dni, String nombre, LocalDate fechaN, String lugar, int distancia, LocalTime marca) {
		super(dni, nombre, fechaN);
		this.lugar = lugar;
		setDistancia(distancia);
		setMarca(marca);
	}

	public Atleta(String dni) {
		super(dni);
		this.lugar = "";
		this.distancia = 0;
		this.marca = null;
	}

	public String getLugar() {
		return lugar;
	}

	public int getDistancia() {
		return distancia;
	}

	public LocalTime getMarca() {
		return marca;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public void setDistancia(int distancia) {
		if (distancia <= 0) {
			throw (new IllegalArgumentException("\nERROR!! La distancia tiene que ser mayor que cero."));
		}
		this.distancia = distancia;
	}

	public void setMarca(LocalTime marca) {
		if (marca == null) {
			throw (new IllegalArgumentException("\nERROR!! Marca sin datos."));
		}
		this.marca = marca;
	}

	public Atleta clone() {
		Atleta copia = new Atleta(super.getDni(), super.getNombre(), super.getFechaNacimiento(), lugar, distancia,
				marca);
		return copia;
	}

	public void leerHora() {

		boolean horaCorrecta;
		LocalTime marca = null;

		do {
			horaCorrecta = true;

			try {
				marca = LocalTime.parse(Teclado.leerString("\nIntroduce el tiempo realizado:"), FORMATOHORA);
				setMarca(marca);
			} catch (DateTimeParseException e) {
				System.out.println("\nERROR!! No se puede analizar este formato de hora.");
				horaCorrecta = false;
			}
		} while (!horaCorrecta);
	}

	// Leer datos
	public void leerDatos() {
		super.leerDatos();
		boolean distanciaCorrecta;
		lugar = Teclado.leerString("\nIntroduce el lugar de la prueba:");

		do {
			distanciaCorrecta = true;

			try {
				distancia = Teclado.leerInt("\nIntroduce la distancia de la prueba:");
				setDistancia(distancia);
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR!! " + e.getMessage() + "\n");
				distanciaCorrecta = false;
			}

			leerHora();

		} while (!distanciaCorrecta);
	}

	// Mostrar datos
	public void mostrarDatos() {
		super.mostrarDatos();
		System.out.println("\nLugar de la prueba: " + lugar);
		System.out.println("\nDistancia de la prueba: " + distancia);
		System.out.println("\nMarca del atleta: " + marca);
	}

	// Leer modo binario
	@SuppressWarnings("deprecation")
	public void leerDatosBinarios(DataInputStream fichero) throws IOException {
		super.leerDatosBinarios(fichero);
		lugar = fichero.readLine();

		try {
			setDistancia(fichero.readInt());
		} catch (IllegalArgumentException e) {
			distancia = 0;
		}

		try {
			setMarca(LocalTime.parse(fichero.readLine()));
		} catch (DateTimeParseException e) {
			marca = null;
		}
	}

	// Escribir modo binario
	public void escribirDatosBinarios(DataOutputStream fichero) throws IOException {
		super.escribirDatosBinarios(fichero);
		fichero.writeBytes(lugar + "\n");
		fichero.writeInt(distancia);
		fichero.writeBytes(marca + "\n");
	}

	// Leer en modo texto
	public String fromCSV(String linea) {
		linea = super.fromCSV(linea);
		String[] datos = linea.split("-");
		lugar = datos[0];

		try {
			setDistancia(Integer.parseInt(datos[1]));
		} catch (IllegalArgumentException e) {
			distancia = 0;
		}

		try {
			setMarca(LocalTime.parse(datos[2], FORMATOHORA));
		} catch (DateTimeParseException e) {
			marca = null;
		}

		linea = "";

		return linea;
	}

	// Escribir en modo texto
	public String toCSV() {
		return super.toCSV() + "-" + lugar + "-" + distancia + "-" + marca;
	}

}
