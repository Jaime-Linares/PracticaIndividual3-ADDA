package tests;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;

import ejercicios.Ejercicio2;
import tipos.Ciudad;
import tipos.Trayecto;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio2 {

	public static void main(String[] args) {
		System.out.println("* TEST EJERCICIO 2 *");
		testsEjercicio2("PI3E2_DatosEntrada");
	}

	
	private static void testsEjercicio2(String fichero) {
		String rutaFichero = "ficheros/" + fichero + ".txt";
		String carpetaResultados = "resultados/ejercicio2/";
		
		Graph<Ciudad,Trayecto> g1 = GraphsReader.newGraph(rutaFichero,
				Ciudad::ofFormat, Trayecto::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Trayecto::precio);
		
		Graph<Ciudad,Trayecto> g2 = GraphsReader.newGraph(rutaFichero,
				Ciudad::ofFormat, Trayecto::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Trayecto::duracion);
		
		// Grafo original
		GraphColors.toDot(g1, 
				carpetaResultados + fichero + ".gv", 
				c -> c.toString(), 
				t -> t.toString(), 
				c -> GraphColors.color(Color.black), 
				t -> GraphColors.color(Color.black));
		
		System.out.println("- Fichero procesado en " + fichero + ".gv en la carpeta " + carpetaResultados);
		
		System.out.println("\nAPARTADO A:");
		List<Set<Ciudad>> solucion = Ejercicio2.fEjercicio2A(g1, fichero);
		System.out.println("- Hay " + solucion.size() + " grupos de ciudades");
		for(int i=0; i<solucion.size(); i++) {
			System.out.println("- Grupo numero " + (i+1) + ": " + 
					solucion.get(i).stream().map(x -> x.nombre()).toList());
		}
		
		System.out.println("\nAPARTADO B:");
		System.out.println("- Grupo de ciudades que maximiza la suma de puntuaciones: " + 
				Ejercicio2.fEjercicio2B(g2, fichero).stream().map(x -> x.nombre()).toList());
		
		System.out.println("\nAPARTADO C:");
		Pair<List<Ciudad>, Double> solucionPair = Ejercicio2.fEjercicio2C(g1, fichero);
		System.out.println("- Grupo de ciudades a visitar que dan lugar que dan lugar al camino cerrado de menor precio:");
		System.out.println(solucionPair.first().stream().map(x -> x.nombre()).toList() + " --> " + solucionPair.second() + " euros");
		
		System.out.println("\nAPARTADO D:");
		List<Set<Ciudad>> datos = Ejercicio2.fEjercicio2A(g2, fichero);
		for(int i=0; i<datos.size(); i++) {
			Trio<Ciudad, Ciudad, Double> solucionD = Ejercicio2.fEjercicio2D(g2, fichero, datos.get(i), i+1);
			System.out.println("- Para el grupo de ciudades " + datos.get(i).stream().map(x -> x.nombre()).toList() +
					", las ciudades no conectadas directamente entre las que se puede viajar en menor tiempo son: ");
			System.out.println("Origen: " + solucionD.first().nombre() + " y Destino: " + solucionD.second().nombre() + 
					" --> " + "Tiempo: " + solucionD.third() + " minutos");
		}
	}
	
	
}
