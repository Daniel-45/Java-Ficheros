package deportistas;

import java.util.Comparator;

/**
 * Un comparador es una clase de apoyo que sera utilizada para los métodos de ordenamiento.
 * Esto se logra implementando la interfaz (java.util.Comparator)
 * 
 * El metodo 'compare' debe devolver lo siguiente:
 * deportista1 < deportista2  -> un numero menor a cero (-1)
 * deportista1 == deportista2 -> cero (0)
 * deportista1 > deportista2  -> un mayor menor a cero (1) 
 * 
 * Para utilizar este comparador, debemos usar el parámetro adicional de Collections.sort().
 */

/**
 * 
 * @author Daniel
 *
 */

public class OrdenarDatosPorFecha implements Comparator<Deportista> {
	
	public int compare(Deportista deportista1, Deportista deportista2) {
		return deportista1.getFechaNacimiento().compareTo(deportista2.getFechaNacimiento());
	}
}
