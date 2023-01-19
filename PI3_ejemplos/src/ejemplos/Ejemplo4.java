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
	 Se desean ubicar c�maras de seguridad en un supermercado de forma que todos los pasillos est�n 
	 vigilados. Se podr� poner una c�mara en cada uno de los cruces entre pasillos. Una c�mara situada 
	 en un cruce puede vigilar todos los pasillos adyacentes.
	 	a) Determine cu�ntas c�maras poner y d�nde ponerlas de forma que se minimice el coste total (es 
	 	decir, el n�mero de c�maras).
		b) Una vez determinado d�nde ubicar las c�maras, se desea realizar la instalaci�n el�ctrica para 
		darles soporte. Para ello, se instalar�n equipos de soporte/gesti�n en algunas c�maras, de forma 
		que cada equipo podr� dar soporte a la c�mara donde est� instalado y a aquellas c�maras conectadas 
		con	ella a trav�s de pasilloss cableados. S�lo se podr�n cablear pasillos que tengan c�maras a ambos 
		extremos. �Cu�ntos equipos son necesarios? �Cu�ntos metros de cable son necesarios?
		c) Muestre el grafo que representa el problema configurando su apariencia de forma que se resalten 
		los cruces en los que hay c�mara y los pasillos cableados.
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
