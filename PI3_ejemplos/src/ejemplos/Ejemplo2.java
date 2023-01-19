package ejemplos;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Carretera;
import datos.Ciudad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;

public class Ejemplo2 {

	public static void fEjemplo2A(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {
	
		// Completar un grafo -> explicit CompletedGraph
		Graph<Ciudad, Carretera> gcompleto = Graphs2.explicitCompleteGraph(
				gf,      // Grafo a completar
				1000000.,   // peso de las nuevas aristas
				Graphs2::simpleWeightedGraph,     // factoria grafo
				() -> Carretera.of(1000000.), // factoria aristas
				Carretera::km);  // ca -> ca.km()  // funcion para obtener los pesos de las aristas
		
		String carpetaResultados = "resultados/ejemplo2/";
		
		GraphColors.toDot(gcompleto,
				carpetaResultados + file + "Apartado A.gv",
				v -> v.nombre(),
				a -> "["+a.km()+"]",
				v -> GraphColors.colorIf(Color.blue, gcompleto.edgesOf(v).stream().anyMatch(e -> ((Carretera)e).km()==1000000000.)),
				a -> GraphColors.colorIf(Color.blue, gcompleto.getEdgeWeight(a)==1000000000.)
				);
	}
	
	private static Ciudad ciudadNombre(Graph<Ciudad, Carretera> g, String nombre) {
		return g.vertexSet().stream()
				.filter(c -> c.nombre().equals(nombre))
				.findFirst()
				.get();
	}
	
	public static void fEjemplo2B(SimpleWeightedGraph<Ciudad, Carretera> gf, String file, String c1, String c2) {
		var alg = new DijkstraShortestPath<>(gf);
		Ciudad origen = ciudadNombre(gf, c1);  // yo
		Ciudad destino = ciudadNombre(gf, c2); // cita
		GraphPath<Ciudad, Carretera> gp = alg .getPath(origen, destino);
		
		String carpetaResultados = "resultados/ejemplo2/";
		
		GraphColors.toDot(gf,
				carpetaResultados + file + "Apartado B.gv",
				v -> v.nombre(),
				a -> "["+a.km()+"]",
				v -> GraphColors.colorIf(Color.blue, gp.getVertexList().contains(v)),
				a -> GraphColors.colorIf(Color.blue, gp.getEdgeList().contains(a))
				);
	}
	
	
}
