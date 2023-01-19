package tipos;

public record Relacion(Integer id) {
	
	private static Integer numId = 0;
	
	public static Relacion of() {
		Integer id = numId;
		numId += 1;
		return new Relacion(id);
	}
	
	public static Relacion ofFormat(String[] partes) {
		return of();
	}

}
