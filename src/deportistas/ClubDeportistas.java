package deportistas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClubDeportistas {

	/*
	 * En esta clase hay que implementar los metodos de las operaciones CRUD. 
	 * C -> Create -> Insert. 
	 * R -> Read -> Select. 
	 * U -> Update -> Update. 
	 * D -> Delete -> Delete.
	 */

	private Map<String, Deportista> deportistas;

	public ClubDeportistas() {
		deportistas = new HashMap<>();
	}

	public Iterator<Deportista> findAll() {
		return deportistas.values().iterator();
	}

	public Deportista findByKey(String dni) {
		return deportistas.get(dni);
	}

	public boolean insert(Deportista d) {

		boolean exito = true;

		// Si el deportista no existe
		if (deportistas.get(d.getDni()) == null) {
			deportistas.put(d.getDni(), d);
		} else {
			exito = false;
		}

		return exito;

	}

	public boolean delete(String dni) {
		// Si el deportista existe
		return deportistas.remove(dni) != null;

	}

	public boolean update(Deportista d) {

		boolean exito = true;

		// Si el deportista existe
		if (deportistas.get(d.getDni()) != null) {
			deportistas.put(d.getDni(), d);
		}

		return exito;
	}
}
