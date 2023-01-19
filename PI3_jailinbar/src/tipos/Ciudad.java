package tipos;


public record Ciudad(String nombre, Integer puntuacion) {
	
	public static Ciudad of(String nombre, Integer puntuacion) {
		return new Ciudad(nombre, puntuacion);
	}
	
	
	public static Ciudad ofFormat(String[] partes) {
		String nombre = partes[0].trim();
		Integer puntuacion = parseaPuntuacion(partes[1].trim());
		return Ciudad.of(nombre, puntuacion);
	}

	private static Integer parseaPuntuacion(String cadena) {
		String res = cadena.substring(0, cadena.indexOf("p"));
		return Integer.valueOf(res);
	}
	
	
	@Override
	public String toString() {
		return this.nombre + " " + this.puntuacion + " puntos";
	}

	
}
