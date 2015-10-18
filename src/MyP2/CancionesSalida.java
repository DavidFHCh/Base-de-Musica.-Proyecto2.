package MyP2;

public class CancionesSalida{

		private String cancion;
		private String anio;
		private String duracion;

		public CancionesSalida(String cancion,String anio,String duracion){
			this.cancion = cancion;
			this.anio = anio;
			this.duracion = duracion;
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

		public String toString(){
			return cancion + ", " + anio + ", " + duracion;
		}
}