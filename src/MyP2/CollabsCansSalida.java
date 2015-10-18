package MyP2;

public class CollabsCansSalida{

		private String cancion;
		private String anio;
		private String duracion;
		private String artista;

		public CollabsCansSalida(String cancion,String anio,String duracion,String artista){
			this.cancion = cancion;
			this.anio = anio;
			this.duracion = duracion;
			this.artista = artista;
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

		public String getArtista(){
			return artista;
		}

		public String toString(){
			return cancion + ", " + anio + ", " + duracion + ", " + artista;
		}
}