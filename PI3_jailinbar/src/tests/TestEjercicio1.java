package tests;

import org.jgrapht.Graph;

import ejercicios.Ejercicio1;
import tipos.Persona;
import tipos.Relacion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio1 {

	public static void main(String[] args) {
		System.out.println("* TEST EJERCICIO 1 *");
		
		System.out.println("\n---------- DATOS DE ENTRADA A ----------");
		testsEjercicio1("PI3E1A_DatosEntrada");
		
		System.out.println("\n---------- DATOS DE ENTRADA B ----------");
		testsEjercicio1("PI3E1B_DatosEntrada");
	}
	
	
	private static void testsEjercicio1(String fichero) {
		String rutaFichero = "ficheros/" + fichero + ".txt";
		String carpetaResultados = "resultados/ejercicio1/";
		
		Graph<Persona,Relacion> g = GraphsReader.newGraph(rutaFichero,
				Persona::ofFormat, Relacion::ofFormat, 
				Graphs2::simpleDirectedGraph);
		
		// Grafo original
		GraphColors.toDot(g, 
				carpetaResultados + fichero + ".gv", 
				p -> p.toString(), 
				r -> "", 
				p -> GraphColors.color(Color.black), 
				r -> GraphColors.color(Color.black));
		
		System.out.println("- Fichero procesado en " + fichero + ".gv en la carpeta " + carpetaResultados);
		
		System.out.println("\nAPARTADO A:");
		System.out.println("- Personas cuyos nombres aparecen en el grafo y cumplen los requisitos: " +
				Ejercicio1.fEjercicio1A(g, fichero).stream().map(x -> x.nombre()).toList());
		
		System.out.println("\nAPARTADO B:");
		if(fichero.equals("PI3E1A_DatosEntrada")) {
			Persona p = Persona.of(13, "Maria", 2008, "Sevilla");
			System.out.println("- Ancestros de " + p.nombre() + ": " +
					Ejercicio1.fEjercicio1B(g, p, fichero).stream().map(x -> x.nombre()).toList());
		} else {
			Persona p = Persona.of(13, "Raquel", 1993, "Sevilla");
			System.out.println("- Ancestros de " + p.nombre() + ": " +
					Ejercicio1.fEjercicio1B(g, p, fichero).stream().map(x -> x.nombre()).toList());
		}
		
		System.out.println("\nAPARTADO C:");
		if(fichero.equals("PI3E1A_DatosEntrada")) {
			Persona p1 = Persona.of(16, "Rafael", 2020, "Malaga");
			Persona p2 = Persona.of(14, "Sara", 2015, "Jaen");
			Persona p3 = Persona.of(13, "Maria", 2008, "Sevilla");
			Persona p4 = Persona.of(12, "Patricia", 2010, "Cordoba");
			Persona p5 = Persona.of(8, "Carmen", 1989, "Jaen");
			System.out.println("- " + p1.nombre() + " y " + p2.nombre() + " son " + Ejercicio1.fEjercicio1C(g, p1, p2));
			System.out.println("- " + p3.nombre() + " y " + p4.nombre() + " son " + Ejercicio1.fEjercicio1C(g, p3, p4));
			System.out.println("- " + p5.nombre() + " y " + p1.nombre() + " son " + Ejercicio1.fEjercicio1C(g, p5, p1));
		} else {
			Persona p1 = Persona.of(14, "Julia", 1996, "Jaen");
			Persona p2 = Persona.of(6, "Angela", 1997, "Sevilla");
			Persona p3 = Persona.of(15, "Alvaro", 2000, "Sevilla");
			Persona p4 = Persona.of(13, "Raquel", 1993, "Sevilla");
			Persona p5 = Persona.of(3, "Laura", 1965, "Jerez");
			System.out.println("- " + p1.nombre() + " y " + p2.nombre() + " son " + Ejercicio1.fEjercicio1C(g, p1, p2));
			System.out.println("- " + p3.nombre() + " y " + p4.nombre() + " son " + Ejercicio1.fEjercicio1C(g, p3, p4));
			System.out.println("- " + p5.nombre() + " y " + p4.nombre() + " son " + Ejercicio1.fEjercicio1C(g, p5, p4));	
		}
		
		System.out.println("\nAPARTADO D:");
		System.out.println("- Personas que tienen hijos/as con distintas personas: " + 
				Ejercicio1.fEjercicio1D(g, fichero).stream().map(x -> x.nombre()).toList());
		
		System.out.println("\nAPARTADO E:");
		System.out.println("- Personas de tal manera que se cubren todas la relaciones existentes: " + 
				Ejercicio1.fEjercicio1E(g, fichero).stream().map(x -> x.nombre()).toList());		
	}

	
}
