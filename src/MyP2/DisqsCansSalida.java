package MyP2;

public class DisqsCansSalida{

		private String cancion;
		private String anio;
		private String duracion;
		private String disquera;

		public DisqsCansSalida(String cancion,String anio,String duracion,String disquera){
			this.cancion = cancion;
			this.anio = anio;
			this.duracion = duracion;
			this.disquera = disquera;
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

		public String getDisquera(){
			return disquera;
		}

		public String toString(){
			return cancion + ", " + anio + ", " + duracion + ", " + disquera;
		}
}