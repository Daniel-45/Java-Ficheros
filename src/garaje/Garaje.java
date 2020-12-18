package garaje;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Garaje {

	private String nombre, direccion;
	private int plazas;
	private Map<String,Vehiculo> vehiculos;

	public Garaje() {
		this.nombre = "";
		this.direccion = "";
		this.plazas = 100;
		this.vehiculos = new TreeMap<String, Vehiculo>();
	}

	public Garaje(String nombre, String direccion, int plazas, Map<String, Vehiculo> vehiculos) {
		this.nombre = nombre;
		this.direccion = direccion;
		setPlazas(plazas);
		this.vehiculos = vehiculos;
	}

	public Garaje(int plazas) {
		this.nombre = "";
		this.direccion = "";
		setPlazas(plazas);
		this.vehiculos = new TreeMap<String, Vehiculo>();
	}

	// Copia
	public Garaje(Garaje original) {
		this.nombre = original.nombre;
		this.direccion = original.direccion;
		this.plazas = original.plazas;
		this.vehiculos = new TreeMap<String, Vehiculo>();
		Vehiculo v;
		Iterator<Vehiculo> it = vehiculos.values().iterator();
		ArrayList<Vehiculo> copia = new ArrayList<Vehiculo>();

		while (it.hasNext()) {
			v = it.next();
			copia.add(v);
		}
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		if (plazas <= 0 || plazas > 100) {
			throw new IllegalArgumentException("\nValor para el número de plazas del garaje no permitido!!");
		}
		this.plazas = plazas;
	}

	// Leer datos
	public void leerDatos() {
		nombre = Teclado.leerString("\nIntroduce el nombre del garaje:");
		direccion = Teclado.leerString("\nIntroduce la dirección del garaje:");
		// setPlazas(Libreria.leerEntre(1, 100, "\nIntroduce el numero de plazas del garaje:"));
	}

	// Leer datos modo binario
	@SuppressWarnings("deprecation")
	public void leerBinario(DataInputStream fichero) throws IOException {
		nombre = fichero.readLine();
		direccion = fichero.readLine();

		try {
			setPlazas(fichero.readInt());
		} catch (IllegalArgumentException e) {
			plazas = 1;
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(DataOutputStream fichero) throws IOException {
		fichero.writeBytes(nombre + "\n");
		fichero.writeBytes(direccion + "\n");
		fichero.writeInt(plazas);
	}

	public String toCSV() {
		String CSV = nombre + ":" + direccion + ":" + plazas;
		CSV += vehiculos.size();

		Iterator<Vehiculo> it = vehiculos.values().iterator();
		ArrayList<Vehiculo> garaje = new ArrayList<Vehiculo>();

		while (it.hasNext()) {
			Vehiculo vehiculo = it.next();
			garaje.add(vehiculo);
		}

		for (Vehiculo v : garaje) {
			CSV += v.toCSV();
		}

		return CSV;
	}

	// Mostrar datos
	@Override
	public String toString() {
		return "Garaje [Nombre = " + nombre + ", Dirección = " + direccion + ", Plazas =" + plazas + ", Vehículos = "
				+ vehiculos + "]";
	}

	public void mostrarDatos() {
		Pantalla.escribirString("\nNombre del garaje: " + nombre);
		Pantalla.escribirString("\nDirección del garaje: " + direccion);
		Pantalla.escribirString("\nNúmero de plazas: " + plazas);

		Iterator<Vehiculo> it = vehiculos.values().iterator();

		while (it.hasNext()) {
			Vehiculo v = it.next();
			v.mostrarDatos();
		}
	}

	// Añadir vehiculo
	public int addVehiculo(Vehiculo v) {
		int exito = -1;

		if (plazas > vehiculos.size()) {
			if (!vehiculos.containsKey(v.getMatricula())) {
				vehiculos.put(v.getMatricula(), v);
				exito = vehiculos.size() - 1;
			} else {
				exito = -2;
			}
		}

		return exito;
	}

	// Calcula cuota
	public float calculaCuota() {
		float cuota = 0;
		Iterator<Vehiculo> it = vehiculos.values().iterator();

		while (it.hasNext()) {
			cuota += it.next().calculaCuota();
		}

		return cuota;
	}

	// Select
	public Iterator<Vehiculo> findAll() {
		return vehiculos.values().iterator();
	}

	public Vehiculo findByKey(String matricula) {
		return vehiculos.get(matricula);
	}

	// Insert
	public boolean insert(Vehiculo v) {
		boolean exito = true;
		if (vehiculos.get(v.getMatricula()) == null) {
			vehiculos.put(v.getMatricula(), v);
		} else {
			exito = false;
		}

		return exito;
	}

	// Update
	public boolean update(Vehiculo v) {
		boolean exito = true;
		if (vehiculos.get(v.getMatricula()) != null) {
			vehiculos.put(v.getMatricula(), v);
		} else {
			exito = false;
		}
		return exito;
	}

	// Delete
	public boolean delete(String matricula) {
		return vehiculos.remove(matricula) != null;
	}
}
