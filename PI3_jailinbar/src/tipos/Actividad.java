package tipos;

public record Actividad(String nombre) {

	public static Actividad of(String nombre) {
		return new Actividad(nombre);
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
}
