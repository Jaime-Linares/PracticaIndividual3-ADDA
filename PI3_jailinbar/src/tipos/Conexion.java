package tipos;

public record Conexion(Integer id) {
	
	private static Integer numId = 0;
	
	public static Conexion of() {
		Integer id = numId;
		numId += 1;
		return new Conexion(id);
	}
	
	public static Conexion ofFormat(String[] partes) {
		return Conexion.of();
	}
	
}
