package deportistas;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class MainDeportistas extends AppMenu {

	private static final String RUTA = "files/";
	private static final String FICHERO = "deportistas.dat";
	private static final String FICHEROCSV = "deportistas.csv";

	private ClubDeportistas club;

	public MainDeportistas() {
		super();
		getOpciones().add("\n1. Insertar deportistas");
		getOpciones().add("\n2. Eliminar deportistas");
		getOpciones().add("\n3. Editar deportistas");
		getOpciones().add("\n4. Listar deportistas");
		getOpciones().add("\n5. Exportar deportistas a CSV");
		getOpciones().add("\n6. Salir de la aplicacion");

		club = new ClubDeportistas();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MainDeportistas app;

		Instant inicio, fin;

		// Hora inicial
		inicio = Instant.now().plusSeconds(7200);
		System.out.println("Hora de inicio: " + inicio);
		File fichero = new File(RUTA + FICHERO);
		System.out.println();
		System.out.println("Nombre del fichero: " + fichero.getName());
		System.out.println();
		System.out.println("Ubicacion del fichero: " + fichero.getAbsolutePath());

		// Crear una App Deportistas
		app = new MainDeportistas();

		// Leer los datos de los ficheros.
		app.leerDatosBinarios();

		app.run();

		/**
		 * Escribir los datos en los ficheros. Ordenados por la fecha de nacimiento de los deportistas.
		 */
		app.escribirDatosBinarios(app.ordenarPorFecha());

		// Hora final
		fin = Instant.now().plusSeconds(7200);

		// Intervalo de segundos que hay entre dos horas.
		Duration duracion = Duration.between(inicio, fin);

		// Convertir para mostrar por pantalla
		LocalTime tiempo = LocalTime.ofSecondOfDay(duracion.getSeconds());

		// Convierte los segundos obtenidos en formato "HH:mm:ss"
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

		// Muestra por pantalla el tiempo de utilizacion de la aplicacion.
		System.out.println("\nEl tiempo de utilizacion de la aplicación ha sido " + tiempo.format(formato));
	}

	// Leer DNI
	public String leerDNI() {
		String dni;
		dni = Teclado.leerString("\nIntroduce el DNI:");
		return dni;
	}

	// Evaluar opcion
	@Override
	public void evaluarOpcion(int opcion) {
		switch (opcion) {
		case 1:
			insertar();
			break;
		case 2:
			eliminar();
			break;
		case 3:
			editar();
			break;
		case 4:
			listar();
			break;
		case 5:
			exportarCSV();
			break;
		default:
			salir();
			break;
		}
	}

	// Leer en modo binario
	@SuppressWarnings("deprecation")
	public void leerDatosBinarios() {

		String tipo;
		Deportista d;
		File fichero = new File(RUTA + FICHERO);

		// Comprobar si el fichero existe -> Si existe lee el fichero.
		if (fichero.exists()) {

			/**
			 * Si le paso como argumentos el bruto y el escritor no tengo que cerrar los
			 * recursos en un bloque finally en el mismo orden que se han abierto.
			 */
			try (FileInputStream bruto = new FileInputStream(fichero);
					DataInputStream lector = new DataInputStream(bruto)) {

				// Si el lector esta disponible
				while (lector.available() > 0) {

					// Indicar el tipo de deportista
					tipo = lector.readLine();

					// Crea una instancia del objeto Class --> Tipo de deportista.
					d = (Deportista) Class.forName(tipo).newInstance();

					d.leerDatosBinarios(lector);

					// Metodo (insert) de la clase ClubDeportistas --> A�ade el deportista al club.
					club.insert(d);
				}

			} catch (IOException e) {
				System.out.println("\nERROR!! Se ha producido un error de acceso al fichero.");
			} catch (InstantiationException e) {
				System.out.println("\nNo se puede crear una instancia del objeto de clase especificado.");
			} catch (IllegalAccessException e) {
				System.out.println("\nERROR!! Se ha producido un error de acceso al fichero.");
			} catch (ClassNotFoundException e) {
				System.out.println("\nERROR!! No se pueden cargar los datos.");
			}
		}
	}

	// Escribir en modo binario
	public void escribirDatosBinarios(List<Deportista> deportistas) {

		String tipo;
		File fichero = new File(RUTA + FICHERO);

		try (FileOutputStream bruto = new FileOutputStream(fichero);
				DataOutputStream escritor = new DataOutputStream(bruto)) {

			// Escribir los datos
			for (Deportista d : deportistas) {
				tipo = d.getClass().getName();
				escritor.writeBytes(tipo + "\n");
				d.escribirDatosBinarios(escritor);
			}
		} catch (FileNotFoundException e) {
			System.out.println("\nERROR!! No se encuentra el fichero.");
		} catch (IOException e) {
			System.out.println("\nERROR!! Se ha producido un error de escritura al fichero.");
		}
	}

	// Ordenar por fecha de nacimiento
	public List<Deportista> ordenarPorFecha() {

		ArrayList<Deportista> ordenados = new ArrayList<>();

		Iterator<Deportista> it = club.findAll();

		while (it.hasNext()) {
			ordenados.add(it.next());
		}

		ordenados.sort(new OrdenarDatosPorFecha());

		return ordenados;
	}

	// Insertar deportistas
	public void insertar() {
		String dni;
		int tipo;
		Deportista d;

		dni = leerDNI();

		//Comprueba si el DNI introducido no esta registrado el la base da datos del club
		if (club.findByKey(dni) == null) {

			// Si no esta pregunta el tipo de deportista
			tipo = Libreria.leerEntre(1, 2, "\nSelecciona el tipo de deportista: 1. Atleta - 2. Ciclista");

			if (tipo == 1) {
				d = new Atleta(dni);
			} else {
				d = new Ciclista(dni);
			}

			d.leerDatos();

			club.insert(d);
		} else {
			System.out.println("\nERROR!! El DNI introducido ya esta registrado en la base de datos del club.");
		}
	}

	// Eliminar deportistas
	public void eliminar() {
		String dni;
		dni = leerDNI();
		if (club.delete(dni)) {
			System.out.println("\nEl deportista se ha eliminado correctamente!!");
		}
	}

	// Editar deportista
	public void editar() {
		String dni;
		Deportista d;
		dni = leerDNI();

		// Comprobar si existe el deportista
		d = club.findByKey(dni);

		if (d != null) {

			// Muestra los datos del deportista.
			System.out.println("\nDatos actuales:");
			d.mostrarDatos();

			// Pregunta si quiero modificar los datos.
			String editar = Teclado.leerString("\n¿Quieres modificar los datos de un deportista? S/N");

			if (editar.equals("S")) {
				// Lee nos nuevos datos del deportista.
				System.out.println("\nIntroducir los nuevos datos");
				d.leerDatos();

				// Actualiza los nuevos datos del deportista en el club.
				club.update(d);

				System.out.println("\nEl deportista se ha actualizado correctamente!!");
			}
		}
		// Si el deportista no existe.
		else {
			System.out.println("\nERROR!! El DNI introducido no esta registrado en la base de datos.");
		}
	}

	// listar deportistas
	@SuppressWarnings("rawtypes")
	public void listar() {

		int tipo;
		Class clase = null;
		Deportista d;

		ArrayList<Deportista> ordenados = new ArrayList<>();

		tipo = Libreria.leerEntre(1, 2, "\nSelecciona el tipo de deportista: 1. Atleta - 2. Ciclista");

		try {

			if (tipo == 1) {
				// Devuelve el objeto asociado con la clase.
				clase = Class.forName("deportistas.Atleta");
			} else {
				// Devuelve el bjeto asociado con la clase.
				clase = Class.forName("deportistas.Ciclista");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("\nERROR!! No se encuentra la clase especificada.");
		}

		Iterator<Deportista> it = club.findAll();

		while (it.hasNext()) {

			d = it.next();

			if (d.getClass().equals(clase)) {
				ordenados.add(d);
			}
		}

		// Ordenar por nombre utilizando el comparador por nombre.
		ordenados.sort(new OrdenarDatosPorNombre());

		for (Deportista deportista : ordenados) {
			deportista.mostrarDatos();
		}
	}

	// Exportar a CSV
	public void exportarCSV() {

		// Colección de tipo List con implementacion de ArrayList
		List<Deportista> deportistas = new ArrayList<>();

		File fichero = new File(RUTA + FICHEROCSV);

		// Guardar en Iterador todos los deportistas.
		Iterator<Deportista> it = club.findAll();

		while (it.hasNext()) {
			deportistas.add(it.next());
			deportistas.sort(new OrdenarDatosPorFecha());
		}

		try (FileWriter bruto = new FileWriter(fichero);
				BufferedWriter buffer = new BufferedWriter(bruto);
				PrintWriter escritor = new PrintWriter(buffer)) {

			for (Deportista d : deportistas) {
				escritor.println(d.getClass().getName() + ":");
				escritor.println(d.getClass().getSimpleName() + ":" + d.toCSV());
			}
		} catch (IOException e) {
			System.out.println("\nERROR!! Se ha producido un error de escritura en el fichero.");
		}
	}

	// Salir
	public void salir() {
		System.out.println("\nGuardando datos...");
		System.out.println("\nHas salido de la aplicación!!");
	}
}
