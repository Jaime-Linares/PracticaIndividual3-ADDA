package tests;

import org.jgrapht.Graph;

import datos.Pasillo;
import ejemplos.Ejemplo4;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo4 {

	public static void main(String[] args) {
		System.out.println("* TEST EJEMPLO 4 *");
		System.out.println("\nSUPERMERCADO 1:");
		testEjemplo4("Supermercado1");
		System.out.println("\nSUPERMERCADO 2:");
		testEjemplo4("Supermercado2");
		System.out.println("\nSUPERMERCADO 3:");
		testEjemplo4("Supermercado3");
	}

	public static void testEjemplo4(String file) {
		String ruta = "ficheros/" + file + ".txt";
		
		Graph<String, Pasillo> grafo = GraphsReader.newGraph(
				ruta,
				v -> v[0], 
				Pasillo::ofFormat, 
				Graphs2::simpleWeightedGraph, 
				Pasillo::mts);
		
		System.out.println("Apartado A): ");
		System.out.println("Las camaras deben colocarse en los siguientes " + 
				Ejemplo4.fEjemplo4A(grafo).size() + " cruces:");
		Ejemplo4.fEjemplo4A(grafo).forEach(c -> System.out.println("\t- Cruce " + c));
		
		System.out.println("Apartado B): ");
		Ejemplo4.fEjemplo4BC(grafo, file);
		
		System.out.println("Apartado C): ");
		System.out.println("- " + file + ".gv generado en " + "resultados/ejemplo4");
	}

	
}
