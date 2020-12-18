package figuras;

import java.util.ArrayList;

import daw.com.Pantalla;
import daw.com.Teclado;
import utilidades.Libreria;

public class MainPruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Figura> figuras = new ArrayList<>();
		
		int tipo;
		Figura f = null;
		String seguir = "";
		
		do
		{
			System.out.println();
			tipo = Libreria.leerEntre(1, 4, "1.Circulo \n\n2.Cuadrado \n\n3.Triángulo \n\n4.Rectángulo \n\nSelecciona una figura:");
			
			if(tipo==1)
			{
				if(f == null)
				f = new Circulo();
			}
			else if(tipo==2)
			{				
				f = new Cuadrado();
			}
			else if(tipo==3) {
				f = new Triangulo();
			}
			else {
				f = new Rectangulo();
			}
			
			f.leerDatos();
			figuras.add(f);
			Pantalla.escribirSaltoLinea();
			seguir = Teclado.leerString("¿Quieres seguir? S/N");
			
		}while(seguir.equalsIgnoreCase("S"));
		
		for(int i = 0; i < figuras.size(); i++)
		{
			figuras.get(i).mostrarDatos();
		}
	}
}
