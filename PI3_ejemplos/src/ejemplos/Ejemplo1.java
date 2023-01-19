package ejemplos;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import datos.Carretera;
import datos.Ciudad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo1 {

	public static void fEjemplo1A(String file, Graph<Ciudad, Carretera> g, Predicate<Ciudad> pv,
			Predicate<Carretera> pa, String nombrevista) {
		// Generar vista del grafo -> SubGraphView
		Graph<Ciudad, Carretera> vista = SubGraphView.of(g, pv, pa);
		
		String carpetaResultados = "resultados/ejemplo1/";
		
		// Tenemos que reslatar los vertices en los que incide mas de una arista
		GraphColors.toDot(vista,            // grafo que queremos pintar 
				carpetaResultados + file + nombrevista + ".gv",            //  nombre del fichero donde queremos que se guarde
				ciudad -> ciudad.nombre(),   // que es lo que va a aparecer en los vertices
				carretera -> carretera.nombre(),
				v -> GraphColors.colorIf(Color.red, vista.edgesOf(v).size() > 1),
				a -> GraphColors.color(Color.blue)
				);						
		System.out.println("\n- " + file + nombrevista + ".gv generado en " + carpetaResultados);	
	}
	
	
}
