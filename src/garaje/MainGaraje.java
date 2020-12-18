package garaje;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daw.com.Pantalla;
import daw.com.Teclado;
import utilidades.AppMenu;
import utilidades.Libreria;

public class MainGaraje extends AppMenu {

	private Garaje garaje;
	private static final String RUTA = "files/";
	private static final String FICHERO = "garaje.dat";
	private static final String FICHEROCSV = "garaje.csv";

	public MainGaraje() {
		super();
		getOpciones().add("\n1.Insetar vehiculo");
		getOpciones().add("\n2.Editar vehiculo");
		getOpciones().add("\n3.Mostrar vehiculo");
		getOpciones().add("\n4.Eliminar vehiculo");
		getOpciones().add("\n5.Mostrar ordenados");
		getOpciones().add("\n6.Mostrar cuota mensual");
		getOpciones().add("\n7.Exportar a CSV");
		getOpciones().add("\n8.Salir de la aplicacion");

		garaje = new Garaje();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MainGaraje app;

		app = new MainGaraje();

		app.leerBinario();

		app.escribirBinario(app.OrdenadosPotencia());

		app.exportarCSV(app.OrdenadosPotencia());

		// app.exportarCSV();

		app.run();
	}

	// Leer datos modo binario
	public void leerBinario() {

		int tipo;
		
		Vehiculo v;

		File fichero = new File(RUTA + FICHEROCSV);

		if (fichero.exists()) {
			System.out.println("\nNombre del fichero: " + fichero.getName());
			System.out.println("\nUbicación del fichero: " + fichero.getAbsolutePath());

			try (FileInputStream bruto = new FileInputStream(fichero);
					DataInputStream lector = new DataInputStream(bruto)) {

				while (lector.available() > 0) {

					tipo = lector.readInt();

					if (tipo == 1) {
						v = new Coche();
						v.leerBinario(lector);
						garaje.insert(v);
					}
				}

			} catch (FileNotFoundException e) {
				System.out.println("\nNo se puede encontrar el fichero...");
			} catch (IOException e) {
				System.out.println("\nError accediendo al fichero...");
			}
		}
	}

	// Escribir datos modo binario
	public void escribirBinario(List<Vehiculo> ordenados) {

		String tipo;

		File fichero = new File(RUTA + FICHERO);

		try (FileOutputStream bruto = new FileOutputStream(fichero);
				DataOutputStream escritor = new DataOutputStream(bruto)) {

			// garaje.escribirBinario(escritor);

			for (Vehiculo v : ordenados) {
				tipo = v.getClass().getName();
				escritor.writeBytes(tipo + "\n");
				v.escribirBinario(escritor);
			}

		} catch (FileNotFoundException e) {
			Pantalla.escribirSaltoLinea();
			System.out.println("\nNo se puede encontrar el fichero...");
		} catch (IOException e) {
			Pantalla.escribirSaltoLinea();
			System.out.println("\nError accediendo al fichero...");
		}
	}

	// Exportar a CSV
	public void exportarCSV(List<Vehiculo> ordenados) {

		String tipo;

		File fichero = new File(RUTA + FICHEROCSV);

		try (FileWriter bruto = new FileWriter(fichero); PrintWriter escritor = new PrintWriter(bruto)) {

			for (Vehiculo v : ordenados) {
				tipo = v.getClass().getName();
				escritor.print(tipo + ":");
				escritor.println(v.toCSV());
			}
		} catch (IOException e) {
			Pantalla.escribirSaltoLinea();
			System.out.println("\nError de acceso al fichero...");
		}
	}

	// Ordenar por potencia
	public List<Vehiculo> OrdenadosPotencia() {

		ArrayList<Vehiculo> ordenados = new ArrayList<Vehiculo>();

		Iterator<Vehiculo> it = garaje.findAll();

		while (it.hasNext()) {
			ordenados.add(it.next());
		}

		ordenados.sort(new OrdenadosPotencia());

		return ordenados;
	}

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
			listar();
			break;
		case 6:
			mostrarCuota();
			break;
		case 7:
			exportarCSV(OrdenadosPotencia());
			break;
		default:
			salir();
			break;
		}
	}

	// Insertar vehiculo
	public void insertar() {
		Vehiculo v;
		int tipo;
		String matricula;

		matricula = Teclado.leerString("\nIntroduce la matricula:");

		if (garaje.findByKey(matricula) == null) {

			tipo = Libreria.leerEntre(1, 2, "\nIntroduce el tipo de vehiculo: 1.Coche - 2.Moto");

			if (tipo == 1) {
				v = new Coche(matricula);
				v.leerDatos();
				// garaje.insert(v);
			} else {
				v = new Moto(matricula);
				v.leerDatos();
				// garaje.insert(v);
			}

			int exito = garaje.addVehiculo(v);

			if (exito >= 0) {

				System.out.println("\nVehiculo insertado correctamente en la plaza " + "[" + exito + "]");
			} else if (exito == -1) {

				System.out.println("\nEl garaje esta completo.");
			} else if (exito == -2) {

				System.out.println("\nEl vehiculo ya existe!!");
			}
		} else {
			System.out.println("\nLa matricula introducida ya esta en la base de datos.");
		}
	}

	// Editar vehiculo
	public void editar() {
		Vehiculo v;
		String matricula = Teclado.leerString("\nIntroduce la matricula del vehiculo a actualizar:");
		v = garaje.findByKey(matricula);

		if (v != null) {
			System.out.println("\nDatos actuales del vehiculo");
			v.mostrarDatos();
			System.out.println("\nIntroduce los nuevos datos");
			v.leerDatos();

			garaje.update(v);
			System.out.println("\nVehiculo actualizado correctamente.");
		} else {
			System.out.println("\nLa matricula introducida no esta en la base de datos.");
		}
	}

	// Eliminar vehiculo
	public void eliminar() {
		String matricula = Teclado.leerString("\nIntroduce la matricula del vehiculo a eliminar:");

		if (garaje.delete(matricula)) {
			System.out.println("\nVehiculo eliminado correctamente.");
		} else {
			System.out.println("\nLa matricula introducida no esta en la base de datos:");
		}
	}

	// Mostrar vehiculo
	public void mostrar() {
		Vehiculo v;
		String matricula = "";
		matricula = Teclado.leerString("\nIntroduce la matricula del vehiculo a mostrar:\n");
		v = garaje.findByKey(matricula);

		if (garaje.findByKey(matricula) != null) {
			System.out.println(v.getClass().getName());
			v.mostrarDatos();
			System.out.println("\nCuota mensual del vehiculo: " + v.calculaCuota() + " euros");
		} else {
			System.out.println("\nLa matricula introducida no esta en la base de datos.");
		}
	}

	// Listar
	public void listar() {

		ArrayList<Vehiculo> ordenados = new ArrayList<Vehiculo>();

		Iterator<Vehiculo> it = garaje.findAll();

		while (it.hasNext()) {

			Vehiculo v = it.next();
			ordenados.add(v);
		}

		ordenados.sort(new OrdenadosPotencia());

		for (Vehiculo vehiculo : ordenados) {
			System.out.println(vehiculo.getClass().getName());
			vehiculo.mostrarDatos();
			System.out.println("\nCuota mensual del vehiculo: " + vehiculo.calculaCuota());
			System.out.println();
		}
	}

	// Mostrar cuota mensual
	public void mostrarCuota() {
		System.out.println("\nTotal a cobrar: " + garaje.calculaCuota() + " euros");
	}

	// Salir
	public void salir() {
		escribirBinario(OrdenadosPotencia());
		System.out.println("\nGuardando datos...");
		System.out.println();
	}
}
