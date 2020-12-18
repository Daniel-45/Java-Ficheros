package figuras;

import daw.com.Teclado;

public abstract class Figura {
	
	private String nombreFigura;
	private int color;
	private float borde;
	private Punto punto; 
	
	public Figura() {
		this(new Punto(),"",1,1);
	}
 	
	public Figura(Punto punto,String nombreFigura,int color,float borde) {
		this.nombreFigura = nombreFigura;
		setColor(color);
		setBorde(borde);
		this.punto = punto;
	}
	
	// Getters
	public String getNombreFigura() {
		return nombreFigura;
	}

	public int getColor() {
		return color;
	}

	public float getBorde() {
		return borde;
	}

	public Punto getPunto() {
		return punto;
	}
	
	
	// Setters
	public void setNombreFigura(String nombreFigura) {
		this.nombreFigura = nombreFigura;
	}

	public void setColor(int color) {

		do {
			if(color < 0 || color > 255) {
				throw(new IllegalArgumentException("\nERROR!! El valor tiene que estar comprendido entre 0 y 255."));
			}
			
		}while(color < 0 || color > 255);

		this.color = color;
	}

	public void setBorde(float borde) {

		do {
			if(borde < 1) {
				throw(new IllegalArgumentException("\nERROR!! El grosor del borde tiene que ser mayor que cero."));
			}

		}while(borde < 1);

		this.borde = borde;
	}

	public void setPunto(Punto punto) {
		this.punto = punto;
	}
	
	// Métodos
	
	// Leer datos
	public void leerDatos() {

		do {
			try {
				punto.leerDatos();
				nombreFigura = Teclado.leerString("\nIntroduce el nombre de la figura:");
				setColor(Teclado.leerInt("\nIntroduce el color de la figura (0 - 255):"));
				setBorde(Teclado.leerFloat("\nIntroduce el grosor del borde:"));
			}
			catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		}while(color < 0 || color > 255 || borde < 1);
	}
	
	// Mostrar datos
	public void mostrarDatos() {
		System.out.println();
		System.out.println("-------------------FIGURA-------------------");
		System.out.println("Nombre: " + nombreFigura);
		System.out.println("Color: " + color);
		System.out.println("Borde: " + borde);
		punto.mostrarDatos();
	}
	
	public String toCsv() {
		return punto.toCsv() + ":" + nombreFigura + ":"  + color + ":" + borde;
	}
	
	public abstract float calcularArea();

	public abstract float calcurarPerimetro();
}
