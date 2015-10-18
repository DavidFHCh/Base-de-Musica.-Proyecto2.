package MyP2;

public class GensCansSalida{

		private String cancion;
		private String anio;
		private String duracion;
		private String genero;

		public GensCansSalida(String cancion,String anio,String duracion,String genero){
			this.cancion = cancion;
			this.anio = anio;
			this.duracion = duracion;
			this.genero = genero;
		}

		public String getCancion(){
			return cancion;
		}

		public String getAnio(){
			return anio;
		}

		public String getDuracion(){
			return duracion;
		}

		public String getGenero(){
			return genero;
		}
}