package figuras;

import daw.com.Teclado;

public class Cuadrado extends Figura {

	private float lado;

	public Cuadrado() {
		super();
		lado = 1;
	}

	public Cuadrado(Punto punto, String nombreFigura, int color, float borde, float lado) {
		super(punto, nombreFigura, color, borde);
		setLado(lado);
	}

	// Getters
	public float getLado() {
		return lado;
	}

	// Setters
	public void setLado(float lado) {

		if (lado < 1) {
			throw (new IllegalArgumentException("\nERROR!! El valor tiene que ser mayor que cero."));
		}
		this.lado = lado;
	}

	// Métodos
	public Cuadrado clone() {
		return new Cuadrado(super.getPunto(), super.getNombreFigura(), super.getColor(), super.getBorde(), lado);
	}

	// Leer datos
	public void leerDatos() {
		do {
			try {
				super.leerDatos();
				lado = Teclado.leerFloat("\nIntroduce el valor del lado del cuadrado:");
				setLado(lado);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} while (lado < 1);
	}

	// Mostrar datos
	public void mostrarDatos() {
		super.mostrarDatos();
		System.out.println();
		System.out.println("Lado: " + lado);
		System.out.println("Área: " + calcularArea());
		System.out.println("Perímetro: " + calcurarPerimetro());
		System.out.println("--------------------------------------------");
	}

	public String toCsv() {
		return super.toCsv() + ":" + lado;
	}

	@Override
	public String toString() {
		return super.toString() + "Cuadrado [Lado = " + lado + "]";
	}

	// Calcular área
	@Override
	public float calcularArea() {
		float area;
		area = lado * lado;
		return area;
	}

	// Calcular perimetro
	@Override
	public float calcurarPerimetro() {
		float perimetro;
		// perimetro = lado + lado + lado +lado;
		perimetro = lado * 4;
		return perimetro;
	}
	
}
