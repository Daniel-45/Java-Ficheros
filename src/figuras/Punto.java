package figuras;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Punto {

	private String nombrePunto;
	private int x, y;

	public Punto() {
		this("", 0, 0);
	}

	public Punto(String nombrePunto, int x, int y) {
		this.nombrePunto = nombrePunto;
		setX(x);
		setY(y);
	}

	// Getters
	public String getnombrePunto() {
		return nombrePunto;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// Setters
	public void setnombrePunto(String nombrePunto) {
		this.nombrePunto = nombrePunto;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	// Métodos
	public Punto clone() {
		Punto copia = new Punto(nombrePunto, x, y);
		return copia;
	}

	// Leer datos
	public void leerDatos() {
		nombrePunto = Teclado.leerString("\nIntroduce el nombre del punto:");
		x = Teclado.leerInt("\nIntroduce la coordenada X:");
		y = Teclado.leerInt("\nIntroduce la coordenada Y:");
	}

	// Mostrar datos
	public void mostrarDatos() {
		Pantalla.escribirSaltoLinea();
		System.out.println("Punto: " + nombrePunto);
		System.out.println("Coordenada X: " + x);
		System.out.println("Coordenada Y: " + y);
		// System.out.println(toCsv());
	}

	public String toCsv() {
		return nombrePunto + ":" + x + ":" + y;
	}

	@Override
	public String toString() {
		return "Punto [Nombre = " + nombrePunto + ", X = " + x + ", Y = " + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	// Comprobar si las coordenadas X e Y son iguales
	public boolean equals(Punto p) {
		return this.x == p.x && this.y == p.y;
	}
}
