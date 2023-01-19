package ejemplos;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;

public class Ejemplo3 {
	
	/* 
	 Se desea ubicar un conjunto de n comensales en mesas, de forma que hay ciertos comensales 
	 que no se pueden sentar en la misma mesa por ser incompatibles entre ellos. Existe simetría 
	 en las incompatibilidades.
	 	a) Diseñe un algoritmo que minimice el número de mesas necesarias para sentar a todos los 
		comensales teniendo en cuenta las incompatibilidades.
		b) Muestre el tamaño y la composición de cada una de las mesas.
		c) Muestre el grafo configurando su apariencia de forma que todos los comensales de la 
		misma mesa se muestren del mismo color.
	 */

	public static void todosApartados(Graph<String, DefaultEdge> gf, String file) {
		var alg = new GreedyColoring<>(gf);
		Coloring<String> solucion = alg.getColoring();
		System.out.println("\n- Mesas necesarias: " + solucion.getNumberColors());
		
		System.out.println("\n- Composicion de mesas: ");
		List<Set<String>> mesas = solucion.getColorClasses();
		for(int i=0; i<mesas.size(); i++) {
			System.out.println("Mesa " + (i+1) + ": " + mesas.get(i).size() + " personas, " + mesas.get(i));
		}
		
		Map<String, Integer> mpColores = solucion.getColors();
		GraphColors.toDot(gf, 
				"resultados/ejemplo3/" + file + ".gv",
				v -> v.toUpperCase(),
				e -> "IMP.",
				v -> GraphColors.color(mpColores.get(v)),
				e -> GraphColors.style(Style.dotted));
		System.out.println("\n- " + file + ".gv generado en " + "resultados/ejemplo3");	
	}
	
	
}
