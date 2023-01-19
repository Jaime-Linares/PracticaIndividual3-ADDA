package ejemplos;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import datos.Pasillo;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo4 {
	
	/*
	 Se desean ubicar cámaras de seguridad en un supermercado de forma que todos los pasillos estén 
	 vigilados. Se podrá poner una cámara en cada uno de los cruces entre pasillos. Una cámara situada 
	 en un cruce puede vigilar todos los pasillos adyacentes.
	 	a) Determine cuántas cámaras poner y dónde ponerlas de forma que se minimice el coste total (es 
	 	decir, el número de cámaras).
		b) Una vez determinado dónde ubicar las cámaras, se desea realizar la instalación eléctrica para 
		darles soporte. Para ello, se instalarán equipos de soporte/gestión en algunas cámaras, de forma 
		que cada equipo podrá dar soporte a la cámara donde esté instalado y a aquellas cámaras conectadas 
		con	ella a través de pasilloss cableados. Sólo se podrán cablear pasillos que tengan cámaras a ambos 
		extremos. ¿Cuántos equipos son necesarios? ¿Cuántos metros de cable son necesarios?
		c) Muestre el grafo que representa el problema configurando su apariencia de forma que se resalten 
		los cruces en los que hay cámara y los pasillos cableados.
	 */

	// APARTADO A
	public static Set<String> fEjemplo4A(Graph<String, Pasillo> gf) {
		var alg = new GreedyVCImpl<>(gf);
		Set<String> cruces = alg.getVertexCover();
		return cruces;		
	}
	
	
	// APARTADO B y C
	public static void fEjemplo4BC(Graph<String, Pasillo> gf, String file) {
		
		// APARTADO B
		Set<String> cruces = fEjemplo4A(gf);
		Predicate<String> pv = c -> cruces.contains(c);
		Predicate<Pasillo> pe = p -> cruces.contains(gf.getEdgeSource(p)) &&
				cruces.contains(gf.getEdgeTarget(p));
		Graph<String, Pasillo> sgf = SubGraphView.of(gf, pv, pe);
		
		var algB1 = new ConnectivityInspector<>(sgf);
		System.out.println("- Numero de equipos necesarios: " + algB1.connectedSets().size());
		
		var algB2 = new KruskalMinimumSpanningTree<>(sgf);
		var tree = algB2.getSpanningTree();
		System.out.println(String.format("- Metros de cable necesarios: %.1f", tree.getWeight()));
		
		
		// APARTADO C
		String carpetaResultados = "resultados/ejemplo4/";
		
		GraphColors.toDot(gf, 
				carpetaResultados + file + ".gv",
				c -> "",
				v -> "",
				v -> GraphColors.colorIf(Color.blue, Color.black, cruces.contains(v)),
				e -> GraphColors.colorIf(Color.blue, Color.blank, tree.getEdges().contains(e)));
		}
	
	
}
