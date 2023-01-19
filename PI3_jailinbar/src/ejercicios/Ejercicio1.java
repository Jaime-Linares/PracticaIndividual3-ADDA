package ejercicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import tipos.Familiar;
import tipos.Persona;
import tipos.Relacion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.List2;
import us.lsi.graphs.views.SubGraphView;

public class Ejercicio1 {
	
	private static String carpetaResultados = "resultados/ejercicio1/";
	
	// APARTADO A
	public static Set<Persona> fEjercicio1A(Graph<Persona,Relacion> gf, String fichero) {
		Set<Persona> res = new HashSet<>();
		Set<Persona> personas = gf.vertexSet();
		
		List<Persona> padres = new ArrayList<>();
		for(Persona persona: personas) {
			padres = Graphs.predecessorListOf(gf, persona);
			if(padres.size()==2 && padres.get(0).ciudad().equals(padres.get(1).ciudad()) &&
					padres.get(0).anyo().equals(padres.get(1).anyo())) {
				res.add(persona);
			}
		} 
	
		Graph<Persona,Relacion> sgf = SubGraphView.of(gf,
				p -> res.contains(p), 
				r -> res.contains(gf.getEdgeSource(r)) && res.contains(gf.getEdgeTarget(r)));
			
		GraphColors.toDot(gf, 
				carpetaResultados + fichero + "_ApartadoA.gv", 
				p -> p.nombre(), 
				r -> "", 
				p -> GraphColors.colorIf(Color.blue, sgf.containsVertex(p)), 
				r -> GraphColors.colorIf(Color.blue, sgf.containsEdge(r)));
			
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoA.gv en la carpeta " + carpetaResultados);
		return res;
	}
	
	
	// APARTADO B
	public static Set<Persona> fEjercicio1B(Graph<Persona,Relacion> gf, Persona persona, String fichero) {
		Set<Persona> res = new HashSet<>();
		
		res = obtieneAncestros(gf, persona, res);
		
		representaGrafo(gf, persona, fichero, res);	
		
		return res;
	}

	private static Set<Persona> obtieneAncestros(Graph<Persona, Relacion> gf, Persona p, Set<Persona> ac) {
		List<Persona> padres = Graphs.predecessorListOf(gf, p);
		for(Persona padre: padres) {
			ac.add(padre);
			ac = obtieneAncestros(gf, padre, ac);
		}
		return ac;
	}
	
	private static void representaGrafo(Graph<Persona,Relacion> gf, Persona persona, String fichero, 
			Set<Persona> res) {
		GraphColors.toDot(gf, 
				carpetaResultados + fichero + "_ApartadoB.gv",
				p -> p.nombre(),
				r -> "",
				p -> GraphColors.colorIf(List2.of(p.equals(persona), res.contains(p)), 
						List2.of(Color.red, Color.blue)),
				r -> GraphColors.color(Color.black));
		
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoB.gv en la carpeta " + carpetaResultados);
	}
	

	// APARTADO C
	public static Familiar fEjercicio1C(Graph<Persona,Relacion> gf, Persona p1, Persona p2) {
		Familiar res = Familiar.OTROS;
		List<Persona> padresp1 = Graphs.predecessorListOf(gf, p1);
		List<Persona> padresp2 = Graphs.predecessorListOf(gf, p2);
		List<Persona> abuelosp1 = new ArrayList<>();
		List<Persona> abuelosp2 = new ArrayList<>();
		
		if(!Collections.disjoint(padresp1, padresp2)) {
			res = Familiar.HERMANOS;
		} else {
			// Metemos abuelos p1
			for(Persona padrep1: padresp1) {
				abuelosp1.addAll(Graphs.predecessorListOf(gf, padrep1));
			}
			// Metemos abuelos p2
			for (Persona padrep2: padresp2) {
				abuelosp2.addAll(Graphs.predecessorListOf(gf, padrep2));
			}
			if(!Collections.disjoint(abuelosp1, abuelosp2)) {
				res = Familiar.PRIMOS;
			}
		}
		
		return res;
	}
	

	// APARTADO D
	public static Set<Persona> fEjercicio1D(Graph<Persona,Relacion> gf, String fichero) {
		Set<Persona> res = new HashSet<>();
		Set<Persona> personas = gf.vertexSet();
		 
		for(Persona persona: personas) {
			List<Persona> hijos = Graphs.successorListOf(gf, persona);
			if(!hijos.isEmpty()) {
				Set<Persona> padresDistintos = new HashSet<>();
				for(Persona hijo: hijos) {
					padresDistintos.addAll(Graphs.predecessorListOf(gf, hijo));
				}
				if(padresDistintos.size() > 2) {
					res.add(persona);
				}		
			}			
		}
				
		GraphColors.toDot(gf, 
				carpetaResultados + fichero + "_ApartadoD.gv",
				p -> p.nombre(),
				r -> "",
				p -> GraphColors.colorIf(Color.blue, res.contains(p)),
				r -> GraphColors.color(Color.black));
		
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoD.gv en la carpeta " + carpetaResultados);
		return res;
	}
	

	// APARTADO E
	public static Set<Persona> fEjercicio1E(Graph<Persona,Relacion> gf, String fichero) {		
		Graph<Persona,Relacion> g = Graphs.undirectedGraph(gf);
	
		var alg = new GreedyVCImpl<>(g);
		Set<Persona> res = alg.getVertexCover();
		
		GraphColors.toDot(g, 
				carpetaResultados + fichero + "_ApartadoE.gv", 
				p -> p.nombre(), 
				r -> "", 
				p -> GraphColors.colorIf(Color.blue, res.contains(p)),
				r -> GraphColors.color(Color.black));
		
		System.out.println("- Fichero procesado en " + fichero + "_ApartadoE.gv en la carpeta " + carpetaResultados);
		return res;
	}
		
	
}
