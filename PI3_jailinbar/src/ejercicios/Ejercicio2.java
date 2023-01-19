package ejercicios;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;

import tipos.Ciudad;
import tipos.Trayecto;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.graphs.views.SubGraphView;

public class Ejercicio2 {

	private static String carpetaResultados = "resultados/ejercicio2/";

	// APARTADO A
	public static List<Set<Ciudad>> fEjercicio2A(Graph<Ciudad,Trayecto> gf, String fichero) {
		var alg = new ConnectivityInspector<>(gf);
		List<Set<Ciudad>> res = alg.connectedSets();
		
		GraphColors.toDot(gf, 
				carpetaResultados + fichero + "_ApartadoA.gv", 
				c -> c.nombre(), 
				t -> "", 
				c -> GraphColors.color(coloreadoCiudad(c, res)), 
				t -> GraphColors.color(coloreadoTrayecto(gf, t, res)));
		
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoA.gv en la carpeta " + carpetaResultados);
		return res;
	}
	
	private static Color coloreadoCiudad(Ciudad ciudad, List<Set<Ciudad>> ls) {
		Color color = null;
		for(Set<Ciudad> sc: ls) {
			if(sc.contains(ciudad)) {
					color = Color.values()[ls.indexOf(sc)];
			}
		}
		return color;
	}
	
	private static Color coloreadoTrayecto(Graph<Ciudad, Trayecto> gf, Trayecto trayecto, 
			List<Set<Ciudad>> ls) {
		Ciudad ciudad = gf.getEdgeSource(trayecto);
		return coloreadoCiudad(ciudad, ls);
	}
	

	// APARTADO B
	public static Set<Ciudad> fEjercicio2B(Graph<Ciudad,Trayecto> gf, String fichero) {
		List<Set<Ciudad>> datos = fEjercicio2A(gf, fichero);
		
		// Valores de referencia
		Set<Ciudad> res = datos.get(0);    
		Integer suma = res.stream().mapToInt(x -> x.puntuacion()).sum();
		
		// Actualizacion de valores
		for(Set<Ciudad> sc: datos) {
			Integer sumaPosible = sc.stream().mapToInt(x -> x.puntuacion()).sum();
			if(sumaPosible > suma) {
				res = sc;
				suma = sumaPosible;
			}
		}
		
		representaGrafo(gf, fichero, res);
		
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoB.gv en la carpeta " + carpetaResultados);
		return res;
	}

	private static void representaGrafo(Graph<Ciudad, Trayecto> gf, String fichero, Set<Ciudad> ls) {
		GraphColors.toDot(gf, 
				carpetaResultados + fichero + "_ApartadoB.gv", 
				c -> c.toString(),
				t -> "",
				c -> GraphColors.colorIf(Color.blue, ls.contains(c)),
				t -> GraphColors.colorIf(Color.blue, ls.contains(gf.getEdgeSource(t))));
	}
	

	// APARTADO C
	public static Pair<List<Ciudad>, Double> fEjercicio2C(Graph<Ciudad,Trayecto> gf, String fichero) {
		var alg = new HeldKarpTSP<Ciudad, Trayecto>();
		List<Set<Ciudad>> datos = fEjercicio2A(gf, fichero);
		
		// Valores referencia
		Set<Ciudad> ciudades = datos.get(0);
		Graph<Ciudad,Trayecto> sfg = SubGraphView.of(gf, ciudades);
		GraphPath<Ciudad,Trayecto> tour = alg.getTour(sfg);
		Double precio = tour.getWeight();
		
		// Actualizacion de valores
		for(Set<Ciudad> sc: datos) {
			Graph<Ciudad,Trayecto> sfgPosible = SubGraphView.of(gf, sc);
			GraphPath<Ciudad,Trayecto> tourPosible = alg.getTour(sfgPosible);
			Double precioPosible = tourPosible.getWeight();
			if(precioPosible < precio) {
				ciudades = sc;
				tour = tourPosible;
				precio = precioPosible;
			}
		}
		
		representaGrafo2(gf, fichero, tour);	
		
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoC.gv en la carpeta " + carpetaResultados);
		return Pair.of(tour.getVertexList(), precio);
	}

	private static void representaGrafo2(Graph<Ciudad, Trayecto> gf, String fichero, GraphPath<Ciudad, Trayecto> tour) {
		GraphColors.toDot(gf, 
				carpetaResultados + fichero + "_ApartadoC.gv", 
				c -> c.nombre(), 
				t -> t.precio().toString() + " euros", 
				c -> GraphColors.colorIf(Color.blue, tour.getVertexList().contains(c)), 
				t -> GraphColors.colorIf(Color.blue, tour.getEdgeList().contains(t)));	
	}


	// APARTADO D
	public static Trio<Ciudad, Ciudad, Double> fEjercicio2D(Graph<Ciudad, Trayecto> gf, String fichero,
			Set<Ciudad> ciudades, Integer num) {
		var alg = new DijkstraShortestPath<>(gf);
		List<Ciudad> ciudadesLista = ciudades.stream().toList();
		
		// Valores referencia
		GraphPath<Ciudad, Trayecto> camino = null;
		Double tiempo = Double.MAX_VALUE;
		
		// Actualizacion de valores
		for(int i=0; i<ciudades.size(); i++) {
			for(int j=i+1; j<ciudades.size(); j++) {
				if(!gf.containsEdge(ciudadesLista.get(i), ciudadesLista.get(j))) {
					GraphPath<Ciudad, Trayecto> caminoPosible = alg.getPath(ciudadesLista.get(i), ciudadesLista.get(j));
					Double tiempoPosible = caminoPosible.getWeight();
					if(tiempo > tiempoPosible) {
						camino = caminoPosible;
						tiempo = tiempoPosible;
					}
				}
			}			
		}
		
		representaGrafo3(gf, fichero, camino, num);		
		
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoD.gv en la carpeta " + carpetaResultados);
		return Trio.of(camino.getStartVertex(), camino.getEndVertex(), tiempo);
	}
	
	private static void representaGrafo3(Graph<Ciudad, Trayecto> gf, String fichero, 
			GraphPath<Ciudad, Trayecto> camino, Integer i) {
		GraphColors.toDot(gf, 
				carpetaResultados + fichero + "_ApartadoD" + i + ".gv",
				c -> c.nombre(), 
				t -> t.duracion().toString() + "minutos",
				c -> GraphColors.colorIf(Color.red, camino.getVertexList().contains(c)),
				t -> GraphColors.colorIf(Color.red, camino.getEdgeList().contains(t)));
	}
		
	
}
