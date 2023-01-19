package ejercicios;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;

import tipos.Actividad;
import tipos.Conexion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

public class Ejercicio3 {

	private static String carpetaResultados = "resultados/ejercicio3/";
	
	// APARTADO A
	public static List<Set<Actividad>> fEjercicio3A(Graph<Actividad,Conexion> gf, String fichero) {
		var alg = new GreedyColoring<>(gf);
		Coloring<Actividad> solucion = alg.getColoring();
		List<Set<Actividad>> res = solucion.getColorClasses();
		return res;	
	}
	
	
	// APARTADO B
	public static void fEjercicio3B(Graph<Actividad,Conexion> gf, String fichero) {
		var alg = new GreedyColoring<>(gf);
		Coloring<Actividad> solucion = alg.getColoring();
		Map<Actividad,Integer> coloresPorActividad = solucion.getColors();
		
		GraphColors.toDot(gf,
				carpetaResultados + fichero + "_ApartadoB.gv",
				a -> a.nombre(),
				c -> "",
				a -> GraphColors.color(coloresPorActividad.get(a)),
				c -> GraphColors.color(Color.black));

		System.out.println("- Fichero procesado en " + fichero + "_ApartadoB.gv en la carpeta " + carpetaResultados);
	}
	
	
}
