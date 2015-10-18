package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;
import javafx.collections.*;
import java.util.List;
import java.util.ArrayList;


/**
*	Clase que modela la tabla de Relaciones entre Colaboraciones y Canciones.
*/
public class Colaboraciones_Canciones{

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
	}

	private static String tabla = "Colaboraciones_Canciones";
	private static String idColaboracion = "id_artista_grupo";
	private static String idCancion = "id_cancion";
	private int idCollab;
	private int idSong;

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idCollab identificador de este renglon. 
	* @param idSong identificador de la cancion.
	*/
	public Colaboraciones_Canciones(int idCollab, int idSong){
		this.idCollab = idCollab;
		this.idSong = idSong;
	}

	/**
	* Constructor vacio.
	*/
	public Colaboraciones_Canciones(){}

	private String updateColaboracion(){
		return "UPDATE " + tabla + " SET " + idColaboracion + " ='" + idCollab + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	private String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idColaboracion + " = " + idCollab + ";";
	}

	private String deleteColaboracion(){
		return "DELETE FROM " + tabla + " WHERE " + idColaboracion + " = '" + idCollab + "';";  
	}

	private String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';";
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + idColaboracion + "," + idCancion + ") " +  " VALUES " + "('" + idCollab + "','" + idSong + "');"; 
	}

	private String selectColaboracion(int idSong){
		return "SELECT " + idColaboracion + " FROM " + tabla + " WHERE " + idCancion + " = " + idSong + ";";
	}

	private String selectCancion(int idCollab){
		return "SELECT " + idCancion + " FROM " + tabla + " WHERE " + idColaboracion + " = " + idCollab + ";";
	}

	private String selectTodo(){
		return "SELECT * FROM " + tabla + ";";
	}

	private String selectTodoIdCancion(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idCancion +";";
	}

	private String selectTodoIdColaboracion(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idColaboracion +";";
	}

	private String joinCancionesAArtistasID(String id){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodoIdCancion(id) + ") ON Artistas.id = " + idColaboracion + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinArtistasACancionesID(String id){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodoIdColaboracion(id) + ") ON Artistas.id = " + idColaboracion + " and Canciones.id = " + idCancion + ";"; 
	}

	@SuppressWarnings("unchecked")
 	private ObservableList<CollabsCansSalida> obtenListaFinal(ResultSet rs){
 		ObservableList<CollabsCansSalida> ol = null;
 		List l = new ArrayList();
 		try{
 			while(rs.next()){
 				l.add(new CollabsCansSalida(rs.getString("cancion"),rs.getString("año"),rs.getString("duracion"),rs.getString("artista")));
 			}	
 		}catch(Exception e){
 			throw new ErrorBaseDeDatos("Error al llenar Lista Final");
 		}
		ol = FXCollections.observableList(l);
 		return ol;
 	} 

	/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: updateColaboracion, updateCancion, deleteColaboracion, deleteCancion, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public synchronized void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "updateColaboracion":
					comando = updateColaboracion();
					stmt.executeUpdate(comando);
					break;
				case "updateCancion":
					comando = updateCancion();
					stmt.executeUpdate(comando);
					break;
				case "deleteColaboracion":
					comando = deleteColaboracion();
					stmt.executeUpdate(comando);
					break;
				case "deleteCancion":
					comando = deleteCancion();
					stmt.executeUpdate(comando);
					break;
				case "insert":
					comando = insert();
					stmt.executeUpdate(comando);
					break;
				default:
					Manejador.cerrarConexion();
					throw new ErrorBaseDeDatos("No conozco esa operacion.");
			}
		}catch(SQLException sqle){
			Manejador.cerrarConexion();
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		Manejador.cerrarConexion();
	}

	/**
	* Realiza operaciones sencillas de busqueda en la tabla.
	* @param operacion puede recibir cualquiera de estas frases: selectTodo,selectCancion,selectColaboracion.
	* @param id para saber que se esta buscando.
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ResultSet realizaBusqueda(String operacion,int id){
		String comando ="";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "selectColaboracion":
					comando = selectColaboracion(id);
					rs = stmt.executeQuery(comando);
					break;
				case "selectCancion":
					comando = selectCancion(id);
					rs = stmt.executeQuery(comando);
					break;
				case "selectTodo":
					comando = selectTodo();
					rs = stmt.executeQuery(comando);
					break;
				default:
					Manejador.cerrarConexion();
					throw new ErrorBaseDeDatos("No conozco esa operacion.");
			}
		}catch(SQLException sqle){
			Manejador.cerrarConexion();
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		return rs;
	}
	
	/**
	* Realiza operaciones no triviales de busqueda entre dos tablas.
	* @param operacion puede recibir cualquiera de estas frases: joinCancionesAColaboracionesIDLike,joinCancionesAColaboracionesIDAnio,joinCancionesAColaboracionesIDEntreAnios,joinCancionesAColaboracionesIDDuracion,joinCancionesAColaboracionesIDEntreDuraciones,joinColaboracionesACancionesID.
	* @param param1 Se le pasan los parametros para realizar la busqueda.
	* @param param2 Se le pasan los parametros para realizar la busqueda. 
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ObservableList<CollabsCansSalida> realizaBusquedaEspecial(String operacion, String param1,String param2){
		String comando = "";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
		ResultSet rs1 = null,rs = null;
		Canciones can = new Canciones();
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "joinCancionesAColaboracionesIDLike":
					rs = can.realizaBusqueda("selectLikeID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
					}
					break;
				case "joinCancionesAArtistasIDAnio":
					rs = can.realizaBusqueda("selectAnioID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
					}
					break;
				case "joinCancionesAArtistasIDEntreAnios":
					rs = can.realizaBusqueda("selectEntreAniosID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
					}
					break;
				case "joinCancionesAArtistasIDDuracion":
					rs = can.realizaBusqueda("selectDuracionID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
					}
					break;
				case "joinCancionesAArtistasIDEntreDuraciones":
					rs = can.realizaBusqueda("selectEntreDuracionesID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
					}
					break;
				case "joinArtistasACancionesID":
					Artistas art = new Artistas();
				    rs = art.realizaBusqueda("selectLikeID",0,param1); //metodo de clase Artistas.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinArtistasACancionesID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
					}	
					break;
				default:
				Manejador.cerrarConexion();
				throw new ErrorBaseDeDatos("No conozco esa operacion."); 
			}
		}catch(SQLException sqle){
			Manejador.cerrarConexion();
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		Manejador.cerrarConexion();
		return obtenListaFinal(rs1);
	}
}