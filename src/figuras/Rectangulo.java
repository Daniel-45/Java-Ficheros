package figuras;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Rectangulo extends Figura {

	private float base, altura;

	public Rectangulo() {
		super();
		base = 1;
		altura = 1;
	}

	public Rectangulo(Punto punto, String nombreFigura, int color, float borde, float base, float altura) {
		super(punto, nombreFigura, color, borde);
		setBase(base);
		setAltura(altura);
	}

	// Getters
	public float getBase() {
		return base;
	}

	public float getAltura() {
		return altura;
	}

	// Setters
	public void setBase(float base) {
		if (base < 1) {
			throw (new IllegalArgumentException("\nERROR!! El valor tiene que ser mayor que cero."));
		}
		this.base = base;
	}

	public void setAltura(float altura) {
		if (altura < 1) {
			throw (new IllegalArgumentException("\nERROR!! El valor tiene que ser mayor que cero."));
		}
		this.altura = altura;
	}

	// Métodos
	public Rectangulo clone() {
		return new Rectangulo(super.getPunto(), super.getNombreFigura(), super.getColor(), 
				super.getBorde(), base, altura);
	}

	// Leer datos
	public void leerDatos() {

		do {
			try {
				super.leerDatos();
				base = Teclado.leerFloat("\nIntroduce la base del rectángulo:");
				setBase(base);

				altura = Teclado.leerFloat("\nIntroduce la altura del rectángulo:");
				setAltura(altura);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} while (base < 1 && altura < 1);
	}

	// Mostrar datos
	public void mostrarDatos() {
		super.mostrarDatos();
		Pantalla.escribirSaltoLinea();
		System.out.println("Base: " + base);
		System.out.println("Altura: " + altura);
		System.out.println("Área: " + calcularArea());
		System.out.println("Perímetro: " + calcurarPerimetro());
		System.out.println("--------------------------------------------");
	}

	public String toCsv() {
		return super.toCsv() + ":" + base + ":" + altura;
	}

	@Override
	public String toString() {
		return super.toString() + "Rectángulo [Base = " + base + ", Altura = " + altura + "]";
	}

	// Calcular área
	@Override
	public float calcularArea() {
		float area;
		area = base * altura;
		return area;
	}

	// Calcular perímetro
	@Override
	public float calcurarPerimetro() {
		float perimetro;
		perimetro = 2 * base + 2 * altura;
		return perimetro;
	}
}
