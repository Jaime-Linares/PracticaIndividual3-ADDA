package tests;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import datos.Carretera;
import datos.Ciudad;
import ejemplos.Ejemplo1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo1 {

	public static void main(String[] args) {
		System.out.println("* TEST EJEMPLO 1 *");
		testsEjemplo1("Andalucia");
		testsEjemplo1("Castilla La Mancha");
	}

	private static void testsEjemplo1(String file) {
		String ruta = "ficheros/" + file + ".txt";
		
		Graph<Ciudad, Carretera> g = GraphsReader.newGraph(ruta, 
				Ciudad::ofFormat, Carretera::ofFormat, 
				Graphs2::simpleWeightedGraph);
		
		String carpetaResultados = "resultados/ejemplo1/";
		
		// Grafo original
		GraphColors.toDot(g,             
				carpetaResultados + file + ".gv",
				ciudad -> ciudad.nombre(),   
				carretera -> carretera.nombre(),
				v -> GraphColors.color(Color.black),
				a -> GraphColors.color(Color.black)
				);
		System.out.println("\n- Archivo " + file + ".txt ha salido procesado");
		
		
		// Primera prueba
		Predicate<Ciudad> pv1 = c -> c.nombre().contains("e");
		Predicate<Carretera> pc1 = ca -> ca.km()<200;
		
		Ejemplo1.fEjemplo1A(file, g, pv1, pc1, " Apartado A");
		
		
		// Segunda prueba
		Predicate<Ciudad> pv2 = c -> c.habitantes() < 500000;
		Predicate<Carretera> pc2 = ca -> ca.km() > 100 && (
				g.getEdgeSource(ca).nombre().length() > 5 ||
				g.getEdgeTarget(ca).nombre().length() > 5);
		
		Ejemplo1.fEjemplo1A(file, g, pv2, pc2, " Apartado B");
		
	}

}
