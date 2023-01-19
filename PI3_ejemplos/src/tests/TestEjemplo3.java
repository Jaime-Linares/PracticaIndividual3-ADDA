package tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import ejemplos.Ejemplo3;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;

public class TestEjemplo3 {

	public static void main(String[] args) {
		System.out.println("* TEST EJEMPLO 3 *");
		testsEjemplo3("Comensales");
	}

	private static void testsEjemplo3(String file) {
		Graph<String, DefaultEdge> g = Graphs2.simpleGraph(String::new, DefaultEdge::new, false);  
		// false por lo de que no tiene peso
		Files2.streamFromFile("ficheros/"+file+".txt").forEach(linea -> {
			// primero siempre poner vertices y despues poner aristas, tiene sentido
			String[] v = linea.split(",");
			g.addVertex(v[0].trim());
			g.addVertex(v[1].trim());
			g.addEdge(v[0].trim(), v[1].trim());
		});
		Ejemplo3.todosApartados(g, file);
	}

}
