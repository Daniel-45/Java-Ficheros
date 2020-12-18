package figuras;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Triangulo extends Figura {
	
	private float ladoBase,lado1,lado2;

	public Triangulo() {
		super();
		ladoBase = 1;
		lado1 = 1;
		lado2 = 1;
	}

	public Triangulo(Punto punto,String nombreFigura,int color,float borde,float ladoBase, float lado1, float lado2) {
		super(punto,nombreFigura,color,borde);
		setladoBase(ladoBase);
		setlado1(lado1);
		setlado2(lado2);
	}

	// Getters
	public float getladoBase() {
		return ladoBase;
	}

	public float getlado1() {
		return lado1;
	}

	public float getlado2() {
		return lado2;
	}

	// Setters
	public void setladoBase(float ladoBase) {
		
		if(ladoBase < 1)
		{
			throw(new IllegalArgumentException());
		}
		this.ladoBase = ladoBase;
	}

	public void setlado1(float lado1) {

		if(lado1 < 1) 
		{
			throw(new IllegalArgumentException());
		}
		this.lado1 = lado1;
	}

	public void setlado2(float lado2) {
		
		if(lado2 < 1) 
		{
			throw(new IllegalArgumentException());
		}
		this.lado2 = lado2;
	}

	// Métodos
	public Triangulo clone() {
		return new Triangulo(super.getPunto(),super.getNombreFigura(),super.getColor(),super.getBorde(),ladoBase,lado1,lado2);
	}
	
	// Leer datos
	public void leerDatos() {
		do
		{
			try {
				super.leerDatos();
				
				ladoBase = Teclado.leerFloat("\nIntroduce el valor de la base:");
				setladoBase(ladoBase);

				lado1 = Teclado.leerFloat("\nIntroduce el valor del primer lado:");
				setlado1(lado1);

				lado2 = Teclado.leerFloat("\nIntroduce el valor del segundo lado:");
				setlado2(lado2);

			}
			catch(IllegalArgumentException e) {
				System.out.println("ERROR!! El valor de los lados tiene que ser mayor que cero.");
			}
		}while(ladoBase < 1 && lado1 < 1 && lado2 < 1);
	}
	
	// Mostrar datos
	public void mostrarDatos() {
		super.mostrarDatos();
		Pantalla.escribirSaltoLinea();
		if(ladoBase==lado1 && ladoBase==lado2 && lado1==lado2) {
			System.out.println("Base: " + ladoBase);
			System.out.println("Lado 1: " + lado1);
			System.out.println("Lado 2: " + lado2);
			System.out.println("Área: " + calcularArea());
			System.out.println("Perímetro: " + calcurarPerimetro());
			Pantalla.escribirSaltoLinea();
			System.out.println("El triángulo es equilátero");
		}
		else if((ladoBase==lado1 && ladoBase != lado2) || (ladoBase==lado2 && ladoBase != lado1) || (lado1==lado2 && lado1 != ladoBase)) {
			System.out.println("Base: " + ladoBase);
			System.out.println("Lado 1: " + lado1);
			System.out.println("Lado 2: " + lado2);
			System.out.println("Área: " + calcularArea());
			System.out.println("Perímetro: " + calcurarPerimetro());
			Pantalla.escribirSaltoLinea();
			System.out.println("El triángulo es isósceles");
		}
		else if((ladoBase != lado1 && ladoBase != lado2 && lado1 != lado2)) {
			System.out.println("Base: " + ladoBase);
			System.out.println("Lado 1: " + lado1);
			System.out.println("Lado 2: " + lado2);
			System.out.println("Área: " + calcularArea());
			System.out.println("Perímetro: " + calcurarPerimetro());
			Pantalla.escribirSaltoLinea();
			System.out.println("El triángulo es escaleno");
		}
		System.out.println("--------------------------------------------");
	}
	
	public String toCsv() {
		return super.toCsv() + ":" + ladoBase + ":" + lado1 + ":" + lado2;
	}

	@Override
	public String toString() {
		return super.toString() + "Tríangulo [Base = " + ladoBase + ", Lado 1 = " + lado1 + ", Lado 2 = " + lado2 + "]";
	}
	
	// Calcular área
	@Override
	public float calcularArea() {
		float area = 0,semiPerimetro;
		semiPerimetro = (ladoBase + lado1 + lado2)/2;
		
		// Fórmula de Heron
		area = (float) (Math.sqrt(semiPerimetro * (semiPerimetro-ladoBase) * (semiPerimetro-lado1) * (semiPerimetro-lado2)));
		
		return area;
	}

	// Calcular perímetro
	@Override
	public float calcurarPerimetro() {
		float perimetro;
		perimetro = ladoBase + lado1 + lado2;
		return perimetro;
	}

}
