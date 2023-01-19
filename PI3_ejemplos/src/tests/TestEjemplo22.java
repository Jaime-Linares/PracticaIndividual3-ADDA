package tests;

import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Carretera;
import datos.Ciudad;
import ejemplos.Ejemplo22;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo22 {

	public static void main(String[] args) {
		System.out.println("* TEST EJEMPLO 2 *");
		testsEjemplo2("Andalucia", "Sevilla", "Almeria");
	}
	

	public static void testsEjemplo2(String file, String origen, String destino) {
		String ruta = "ficheros/" + file + ".txt";
		
		SimpleWeightedGraph<Ciudad, Carretera> g = GraphsReader.newGraph(
				ruta,
				Ciudad::ofFormat,
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph,
				Carretera::km);
		
		System.out.println("\n- Archivo " + file + ".txt ha salido procesado");
		// System.out.println("Datos de entrada: " + g);
		
		System.out.println("Apartado A):");
		Ejemplo22.fEjemplo2A(g, file);
		
		System.out.println("Apartado B):");
		Ejemplo22.fEjemplo2B(g, file, origen, destino);
		
		System.out.println("Apartado C):");
		Ejemplo22.fEjemplo2C(g, file);
		
		System.out.println("Apartado D):");
		Ejemplo22.fEjemplo2D(g, file);
	}

	
}
