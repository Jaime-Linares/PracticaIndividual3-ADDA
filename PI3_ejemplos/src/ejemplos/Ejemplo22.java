package ejemplos;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Carretera;
import datos.Ciudad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.Graphs2;

public class Ejemplo22 {

	// APARTADO A
	public static void fEjemplo2A(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {
				
		// Completar un grafo -> explicitCompletedGraph
		Graph<Ciudad,Carretera> g = Graphs2.explicitCompleteGraph(
				gf,     // Grafo a completar
				10000.,    // peso de las nuevas aristas
				Graphs2::simpleWeightedGraph,    // factoria grafo
				() -> Carretera.of(10000.),     // factoria aristas
				Carretera::km);    // funcion para obtener los pesos de las aristas
		
		String carpetaResultados = "resultados/ejemplo2/";
		
		GraphColors.toDot(g,
				carpetaResultados + file + "Apartado A.gv",
				v -> v.nombre(),
				a -> "["+a.km()+"]",
				v -> GraphColors.colorIf(Color.blue, g.edgesOf(v).stream().anyMatch(e -> ((Carretera)e).km()==10000.)),
				a -> GraphColors.colorIf(Color.blue, g.getEdgeWeight(a)==10000.)
				);
		
		System.out.println("\n- " + file + ".gv generado en " + carpetaResultados);
	}
	
	
	// APARTADO B
	public static void fEjemplo2B(SimpleWeightedGraph<Ciudad, Carretera> gf, String file, 
			String c1, String c2) {
		var alg = new DijkstraShortestPath<>(gf);
		Ciudad origen = ciudadNombre(gf, c1);
		Ciudad destino = ciudadNombre(gf, c2);
		GraphPath<Ciudad, Carretera> gp = alg.getPath(origen, destino);
		
		String carpetaResultados = "resultados/ejemplo2/";
		
		GraphColors.toDot(gf,
				carpetaResultados + file + "Apartado B.gv",
				v -> v.nombre(),
				a -> "["+a.km()+"]",
				v -> GraphColors.colorIf(Color.blue, gp.getVertexList().contains(v)),
				a -> GraphColors.colorIf(Color.blue, gp.getEdgeList().contains(a))
				);
		
		System.out.println("\n- " + file + ".gv generado en " + carpetaResultados);
		
	}
	
	private static Ciudad ciudadNombre(Graph<Ciudad, Carretera> g, String nombre) {
		return g.vertexSet().stream()
				.filter(c -> c.nombre().equals(nombre))
				.findFirst()
				.get();
	}
	
	
	// APARTADO C
	public static void fEjemplo2C(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {
		Graph<Ciudad, Carretera> g = Graphs2.toDirectedWeightedGraph(gf,
				x -> Carretera.of(x.km(), x.nombre()));
		
		String carpetaResultados = "resultados/ejemplo2/";
		
		GraphColors.toDot(
				g,
				carpetaResultados + file + "Apartado C.gv",
				x -> x.nombre(),
				x -> x.nombre(),
				v -> GraphColors.color(Color.black),
				e -> GraphColors.style(Style.solid));
		
		System.out.println("\n- " + file + ".gv generado en " + carpetaResultados);
	}
	
	
	// APARTADO D
	public static void fEjemplo2D(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {
		var alg = new ConnectivityInspector<>(gf);
		List<Set<Ciudad>> ls = alg.connectedSets();
		System.out.println("Hay " + ls.size() + " componentes conexas.");
		
		String carpetaResultados = "resultados/ejemplo2/";
		
		GraphColors.toDot(gf, 
				carpetaResultados + file + "Apartado D.gv",
				x -> x.nombre(),
				x -> x.nombre(),
				v -> GraphColors.color(asignaColor(v, ls, alg)),
				e -> GraphColors.color(asignaColor(gf.getEdgeSource(e), ls, alg)));
		
		System.out.println("\n- " + file + ".gv generado en " + carpetaResultados);
	}
		
	private static Color asignaColor(Ciudad v, List<Set<Ciudad>> ls, ConnectivityInspector<Ciudad, Carretera> alg) {
		Color[] vc = Color.values();
		Set<Ciudad> s = alg.connectedSetOf(v);
		return vc[ls.indexOf(s)];
	}
	
	
}
