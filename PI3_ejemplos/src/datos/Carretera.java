package datos;

public record Carretera(Integer id, Double km, String nombre) {

	private static int num =0;

	public static Carretera ofFormat(String[] formato) {
		Double km = Double.parseDouble(formato[3]);
		String nomb = formato[2];		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}

	public static Carretera of(Double kms) {
		Double km = kms;
		String nomb = null;		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}

	// Necesario para el Ejemplo 2 c)
	public static Carretera of(Double kms, String nombre) {
		Double km = kms;
		String nomb = nombre;           
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}

	@Override
	public String toString() {
		String nn = this.nombre==null?"":this.nombre+",";
		return nn+this.km+")";
	}

}
