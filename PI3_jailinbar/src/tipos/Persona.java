package tipos;

public record Persona(Integer id, String nombre, Integer anyo, String ciudad) {
	
	public static Persona of(Integer id, String nombre, Integer anyo, String ciudad) {
		return new Persona(id, nombre, anyo, ciudad);
	}
	
	public static Persona ofFormat(String[] partes) {
		Integer id = Integer.valueOf(partes[0].trim());
		String nombre = partes[1].trim();
		Integer anyo = Integer.valueOf(partes[2].trim());
		String ciudad = partes[3].trim();
		return of(id, nombre, anyo, ciudad);
	}
	
	@Override
	public String toString() {
		return this.nombre + " " + this.anyo + " " + this.ciudad;
	}

}
