package figuras;

import daw.com.Teclado;

public class Circulo extends Figura {

	private float radio;

	public Circulo() {
		super();
		radio = 1;
	}

	public Circulo(Punto punto, String nombreFigura, int color, float borde, float radio) {
		super(punto, nombreFigura, color, borde);
		setRadio(radio);
	}

	// Getters
	public float getRadio() {
		return radio;
	}

	// Setters
	public void setRadio(float radio) {

		if (radio < 1) {
			throw (new IllegalArgumentException("\nERROR!! El valor tiene que ser mayor que cero."));
		}
		this.radio = radio;
	}

	// Métodos
	public Circulo clone() {
		return new Circulo(super.getPunto(), super.getNombreFigura(), super.getColor(), super.getBorde(), radio);
	}

	// Leer datos
	public void leerDatos() {

		do {
			try {

				super.leerDatos();
				radio = Teclado.leerFloat("\nIntroduce el radio del círculo:");
			} catch (IllegalArgumentException e) {
				System.out.println("\nERROR!! El valor del radio tiene que ser mayor que cero.");
			}

		} while (radio < 1);
	}

	// Mostrar datos
	public void mostrarDatos() {
		super.mostrarDatos();
		System.out.println();
		System.out.println("Radio: " + radio);
		System.out.println("Área: " + calcularArea());
		System.out.println("Perímetro: " + calcurarPerimetro());
		System.out.println("--------------------------------------------");
	}

	public String toCsv() {
		return super.toCsv() + ":" + radio;
	}
	

	// Calcular área
	@Override
	public float calcularArea() {
		float area = 0;
		area = (float) (Math.PI * (radio * radio));
		return area;
	}

	// Calcular perímetro
	@Override
	public float calcurarPerimetro() {
		float perimetro = 0;
		perimetro = (float) (2 * Math.PI * 2);
		return perimetro;
	}

}
