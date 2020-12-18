package escalada;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import daw.com.Teclado;
import utilidades.Libreria;

/**
 * 
 * @author Daniel
 *
 */

public class Deportiva extends Via {

	private int seguros;
	private LocalDate fechaR;
	private static final float COSTE = 2000;
	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public Deportiva() {
		super();
		this.seguros = 0;
		this.fechaR = null;
	}

	public Deportiva(String nombre, int grado, int longitud, int seguros, LocalDate fechaR) {
		super(nombre, grado, longitud);
		setSeguros(seguros);
		setFechaR(fechaR);
	}

	public Deportiva(String nombre) {
		super(nombre);
		setSeguros(seguros);
		fechaR = null;
	}

	public int getSeguros() {
		return seguros;
	}

	public LocalDate getFechaR() {
		return fechaR;
	}

	public void setSeguros(int seguros) {
		if (seguros < 0) {
			throw new IllegalArgumentException("\nValor no permitido para el numero de seguros de la vía!!");
		}
		this.seguros = seguros;
	}

	public void setFechaR(LocalDate fechaR) {
		if (fechaR == null) {
			throw new IllegalArgumentException("\nFecha sin datos!!");
		} else if (fechaR.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("\nValor no permitido para la fecha de revisión de la vía!!");
		}
		this.fechaR = fechaR;
	}

	// Leer fecha
	public void leerFecha() {
		boolean fechaCorrecta;
		String fechaTexto;

		do {
			fechaCorrecta = true;

			try {
				fechaTexto = Teclado.leerString("\nIntroduce la fecha de revisión de la vía:");
				setFechaR(LocalDate.parse(fechaTexto, FORMATO));
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR!! Valor no permitido para la fecha de revisión de la vía!!\n");
				fechaCorrecta = false;
			} catch (DateTimeParseException e) {
				System.out.println("\nERROR!! No se puede analizar este formato de fecha\n");
				fechaCorrecta = false;
			}

		} while (!fechaCorrecta);
	}

	// Escribir fecha
	public String escribirFecha() {
		String fecha;
		if (fechaR == null) {
			fecha = "Fecha sin datos.";
		} else {
			fecha = fechaR.format(FORMATO);
		}

		return fecha;
	}

	// Leer datos
	public void leerDatos() {
		super.leerDatos();

		try {
			setSeguros(Libreria.leerPositivo("\nIntroduce el número de seguros de la vía:"));
		} catch (IllegalArgumentException e) {
			seguros = 0;
		}

		leerFecha();
	}

	// Leer datos modo binario
	@SuppressWarnings("deprecation")
	public void leerBinario(DataInputStream fichero) throws IOException {
		super.leerBinario(fichero);

		try {
			setSeguros(fichero.readInt());
		} catch (IllegalArgumentException e) {
			seguros = 0;
		}

		try {
			setFechaR(LocalDate.parse(fichero.readLine(), FORMATO));
		} catch (DateTimeParseException e) {
			fechaR = null;
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		super.escribirBinario(fichero);
		fichero.writeInt(seguros);
		fichero.writeBytes(escribirFecha() + "\n");
	}

	// Escribir datos modo texto (CSV)
	public String toCSV() {
		return super.toCSV() + ":" + seguros + ":" + fechaR;
	}

	// Mostrar datos
	@Override
	public String toString() {
		return super.toString() + "Deportiva [Seguros = " + seguros + ", Fecha Revisión = " + fechaR + "]";
	}

	public void mostrarDatos() {
		super.mostrarDatos();
		System.out.println("\nNúmero de seguros de la vía: " + seguros);
		System.out.println("\nFecha de la última revisión: " + escribirFecha());
	}

	// Calcular rescate
	@Override
	public float calculaRescate() {
		float resultado;
		resultado = COSTE + (100 * super.getLongitud() / seguros);
		return resultado;
	}
}
