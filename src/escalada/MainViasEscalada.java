package escalada;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import daw.com.Teclado;
import utilidades.AppMenu;
import utilidades.Libreria;

/**
 * 
 * @author Daniel
 *
 */

public class MainViasEscalada extends AppMenu {

	// Atributos
	private ViasEscalada vias;
	private static final String RUTA = "files/";
	private static final String FICHERO = "vias.dat";
	private static final String FICHEROCSV = "vias.csv";

	// Constructor
	public MainViasEscalada() {
		super();
		getOpciones().add("\n1.Insertar una vía");
		getOpciones().add("\n2.Actualizar una vía");
		getOpciones().add("\n3.Mostrar una vía");
		getOpciones().add("\n4.Eliminar una vía");
		getOpciones().add("\n5.Listar vías por tipo");
		getOpciones().add("\n6.Listar todas las vías");
		getOpciones().add("\n7.Exportar a fichero CSV");
		getOpciones().add("\n8.Salir de la aplicación");

		vias = new ViasEscalada();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MainViasEscalada app;

		Instant inicio, fin;

		inicio = Instant.now().plusSeconds(7200);
		
		System.out.println("\nHora de inicio de la aplicacion: " + inicio);

		app = new MainViasEscalada();

		app.leerBinario();

		app.run();

		fin = Instant.now().plusSeconds(7200);

		Duration duracion = Duration.between(inicio, fin);

		LocalTime tiempo = LocalTime.ofSecondOfDay(duracion.getSeconds());

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		System.out.println("\nTiempo de utilizacion de la aplicacion: " + tiempo.format(formato));

	}

	// Métodos
	@Override
	public void evaluarOpcion(int opcion) {

		switch (opcion) {
		case 1:
			insertar();
			break;
		case 2:
			editar();
			break;
		case 3:
			mostrar();
			break;
		case 4:
			eliminar();
			break;
		case 5:
			listarTipo();
			break;
		case 6:
			listarTodas();
			break;
		case 7:
			exportarCSV(ordenarGrado());
			break;
		default:
			salir();
			break;
		}
	}

	// Ordenar por grado
	public List<Via> ordenarGrado() {
		ArrayList<Via> ordenadas = new ArrayList<Via>();

		Iterator<Via> it = vias.findAll();

		while (it.hasNext()) {
			ordenadas.add(it.next());
		}

		ordenadas.sort(new OrdenaGrado());

		return ordenadas;
	}

	// Leer datos modo binario
	@SuppressWarnings("deprecation")
	public void leerBinario() {

		Via v;
		String tipo;

		File fichero = new File(RUTA + FICHERO);

		if (fichero.exists()) {
			
			System.out.println("\nNombre del fichero: " + fichero.getName());
			
			System.out.println("\nUbicacion del fichero: " + fichero.getAbsolutePath());

			try (FileInputStream bruto = new FileInputStream(fichero);
					DataInputStream lector = new DataInputStream(bruto)) {

				while (lector.available() > 0) {

					tipo = lector.readLine();
					v = (Via) Class.forName(tipo).newInstance();
					v.leerBinario(lector);
					vias.insert(v);
				}
			} catch (IOException e) {
				System.out.println();
				System.out.println("\nError accediendo al fichero...");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				System.out.println();
				System.out.println("\nNo se pueden cargar los datos...");
			}
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(List<Via> ordenadas) {

		String tipo;

		File fichero = new File(RUTA + FICHERO);

		try (FileOutputStream bruto = new FileOutputStream(fichero);
				DataOutputStream escritor = new DataOutputStream(bruto)) {

			for (Via v : ordenadas) {
				tipo = v.getClass().getName();
				escritor.writeBytes(tipo + "\n");
				v.escribirBinario(escritor);
			}

		} catch (IOException e) {
			System.out.println("\nError de acceso al fichero.");
		}
	}

	// Escribir datos modo texto (CSV)
	public void exportarCSV(List<Via> ordenadas) {

		String tipo;

		File fichero = new File(RUTA + FICHEROCSV);

		try (FileWriter bruto = new FileWriter(fichero); PrintWriter escritor = new PrintWriter(bruto)) {

			for (Via v : ordenadas) {
				tipo = v.getClass().getName();
				escritor.print(tipo + "\n");
				escritor.println(v.toCSV());
			}

		} catch (IOException e) {
			System.out.println("\nError de acceso al fichero.");
		}
	}

	// Insertar
	public void insertar() {

		Via v;
		int tipo;
		String nombre;

		nombre = Teclado.leerString("\nIntroduce el nombre de la vía:");

		if (vias.findByKey(nombre) == null) {

			tipo = Libreria.leerEntre(1, 2, "\nIntroduce el tipo de vía: 1.Deportiva - 2.Clasica");

			if (tipo == 1) {

				v = new Deportiva(nombre);
				v.leerDatos();
				vias.insert(v);
			} else {

				v = new Clasica(nombre);
				v.leerDatos();
				vias.insert(v);
			}

			System.out.println("\nLa vía se ha dado de alta correctamente.");
			
		} else {
			System.out.println("\nEl nombre introducido ya esta en la base de datos.");
		}
	}

	// Actualizar
	public void editar() {

		Via v;
		String nombre;

		nombre = Teclado.leerString("\nIntroduce el nombre de la vía:");

		v = vias.findByKey(nombre);

		if (v != null) {
			System.out.println("\nDatos actuales de la vía:");
			v.mostrarDatos();
			System.out.println("\nIntroduce los nuevos datos");
			v.leerDatos();

			vias.update(v);
			System.out.println("\nvía actualizada correctamente.");
		} else {
			System.out.println("\nEl nombre introducido no está en la base de datos.");
		}
	}

	// Mostrar
	public void mostrar() {

		Via v;
		String nombre;

		nombre = Teclado.leerString("\nIntroduce el nombre de la vía:");

		v = vias.findByKey(nombre);

		if (v != null) {

			v.mostrarDatos();
			System.out.println("\nEl coste del rescate en caso de accidente es: " + v.calculaRescate() + " euros");
		} else {
			System.out.println("\nEl nombre introducido no está en la base de datos.");
		}
	}

	// Eliminar
	@SuppressWarnings("unused")
	public void eliminar() {

		Via v;
		String nombre;

		nombre = Teclado.leerString("\nIntroduce el nombre de la vía:");

		if (vias.delete(nombre)) {
			System.out.println("\nVía eliminada correctamente.");
		} else {
			System.out.println("\nEl nombre introducido no está en la base de datos.");
		}
	}

	// Listar todas
	public void listarTodas() {

		ArrayList<Via> ordenadas = new ArrayList<Via>();

		Iterator<Via> it = vias.findAll();

		while (it.hasNext()) {

			Via v = it.next();
			ordenadas.add(v);
		}

		ordenadas.sort(new OrdenaGrado());

		for (Via via : ordenadas) {
			via.mostrarDatos();
			System.out.println("\nEl coste del rescate en caso de accidente es: " + via.calculaRescate() + " euros");
		}
	}

	// Listar por tipo
	@SuppressWarnings("rawtypes")
	public void listarTipo() {

		int tipo, longitud;
		Class clase = null;
		ArrayList<Via> ordenadas = new ArrayList<Via>();

		tipo = Libreria.leerEntre(1, 2, "\nIntroduce el tipo de vía: 1.Deportiva - 2.Clasica");

		try {

			if (tipo == 1) {

				clase = Class.forName("escalada.Deportiva");
			} else {
				clase = Class.forName("escalada.Clasica");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("\nERROR!! No se encuentra la clase especificada.");
		}

		longitud = Teclado.leerInt("\nIntroduce la longitud deseada:");

		Iterator<Via> it = vias.findAll();

		while (it.hasNext()) {

			Via via = it.next();

			if (via.getLongitud() >= longitud && via.getClass().equals(clase)) {
				ordenadas.add(via);
			}
		}

		ordenadas.sort(new OrdenaGrado());

		for (Via v : ordenadas) {
			v.mostrarDatos();
			System.out.println("\nEl coste del rescate en caso de accidente es: " + v.calculaRescate() + " euros");
		}
	}

	// Salir
	public void salir() {
		escribirBinario(ordenarGrado());
		System.out.println("\nGuardando datos...");
	}
}
