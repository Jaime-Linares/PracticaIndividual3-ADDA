package tipos;

public record Trayecto(Integer id, Double precio, Double duracion) {
	
	private static Integer numId = 0;

	public static Trayecto of(Double precio, Double duracion) {
		Integer id = numId;
		numId += 1;
		return new Trayecto(id, precio, duracion);
	}
	
	
	public static Trayecto ofFormat(String[] partes) {
		Double precio = parseaPrecio(partes[2].trim());
		Double duracion = parseaDuracion(partes[3].trim());
		return Trayecto.of(precio, duracion);
	}

	private static Double parseaPrecio(String cadena) {
		String res = cadena.substring(0, cadena.indexOf("e"));
		return Double.valueOf(res);
	}

	private static Double parseaDuracion(String cadena) {
		String res = cadena.substring(0, cadena.indexOf("m"));
		return Double.valueOf(res);
	}
	
	
	@Override
	public String toString() {
		return this.precio + " euros" + " " + this.duracion + " minutos";
	}
	
	
}
