package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;

import ejercicios.Ejercicio3;
import tipos.Actividad;
import tipos.Conexion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.streams.Stream2;

public class TestEjercicio3 {

	public static void main(String[] args) {
		System.out.println("* TEST EJERCICIO 3 *");
		
		System.out.println("\n---------- DATOS DE ENTRADA A ----------");
		testsEjercicio3("PI3E3A_DatosEntrada");
		
		System.out.println("\n---------- DATOS DE ENTRADA B ----------");
		testsEjercicio3("PI3E3B_DatosEntrada");
	}

	
	private static void testsEjercicio3(String fichero) {
		String rutaFichero = "ficheros/" + fichero + ".txt";
		String carpetaResultados = "resultados/ejercicio3/";
		
		Graph<Actividad,Conexion> g = Graphs2.simpleGraph();
		
		fRellenaGrafo(g, rutaFichero);
		
		// Grafo original
		GraphColors.toDot(g, 
				carpetaResultados + fichero + ".gv", 
				a -> a.nombre(), 
				c -> "", 
				a -> GraphColors.color(Color.black), 
				c -> GraphColors.color(Color.black));
		
		System.out.println("- Fichero procesado en " + fichero + ".gv en la carpeta " + carpetaResultados);
		
		System.out.println("\nAPARTADO A:");
		List<Set<Actividad>> sol = Ejercicio3.fEjercicio3A(g, fichero);
		System.out.println("- Numero de franjas horarias necesarias: " + sol.size());
		System.out.println("- Actividades para mostrarse en paralelo por franja horaria: ");
		for(int i=0; i<sol.size(); i++) {
			System.out.println("   Franja numero " + (i+1) + ": " + sol.get(i));
		}
		
		System.out.println("\nAPARTADO B:");
		Ejercicio3.fEjercicio3B(g, fichero);
	}

	
	private static void fRellenaGrafo(Graph<Actividad, Conexion> g, String rutaFichero) {
		List<String> datos = Stream2.file(rutaFichero).toList();
		
		for(String linea: datos) {
			String[] partes = linea.split(":");
			String datosPartes = partes[1].trim();
			String[] actividadesPartes = datosPartes.split(",");
			
			List<Actividad> actividades = new ArrayList<>();
			
			for(String actividadStr: actividadesPartes) {
				Actividad actividad = Actividad.of(actividadStr.trim());
				g.addVertex(actividad);
				actividades.add(actividad);
			}
			
			for(int i=0; i<actividades.size(); i++) {
				for(int j=i+1; j<actividades.size(); j++) {
					g.addEdge(actividades.get(i), actividades.get(j), Conexion.of());
				}
			}
		}		
	}

	
}
