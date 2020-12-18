package escalada;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Daniel
 *
 */

public class ViasEscalada {

	private Map<String, Via> vias;

	public ViasEscalada() {
		vias = new HashMap<String, Via>();
	}

	// Select
	public Iterator<Via> findAll() {
		return vias.values().iterator();
	}

	public Via findByKey(String nombre) {
		return vias.get(nombre);
	}

	// Insert
	public boolean insert(Via v) {
		boolean exito = true;

		if (vias.get(v.getNombre()) == null) {
			vias.put(v.getNombre(), v);
		} else {
			exito = false;
		}

		return exito;
	}

	// Update
	public boolean update(Via v) {
		boolean exito = true;

		if (vias.get(v.getNombre()) != null) {
			vias.put(v.getNombre(), v);
		} else {
			exito = false;
		}

		return exito;
	}

	// Delete
	public boolean delete(String nombre) {
		return vias.remove(nombre) != null;
	}
}
