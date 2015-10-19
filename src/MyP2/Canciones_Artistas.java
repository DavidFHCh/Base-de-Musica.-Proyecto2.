package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;


/**
*	Clase que modela la tabla de Relaciones entre Artistas y Canciones.
*/
public class Canciones_Artistas{

	private static String tabla = "Canciones_Artistas";
	private static String idArtista = "id_artistas";
	private static String idCancion = "id_cancion";
	private int idArtist;
	private int idSong;

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idArtist identificador de este renglon. 
	* @param idSong identificador de la cancion.
	*/
	public Canciones_Artistas(int idArtist, int idSong){
		this.idArtist = idArtist;
		this.idSong = idSong;
	}

	/**
	* Constructor vacio.
	*/
	public Canciones_Artistas(){}

	private String updateArtista(){
		return "UPDATE " + tabla + " SET " + idArtista + " ='" + idArtist + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	private String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idArtist + " = " + idArtista + ";";
	}

	private String deleteArtista(){
		return "DELETE FROM " + tabla + " WHERE " + idArtista + " = '" + idArtist + "';";
	}

	private String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';";  
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + idArtista + "," + idCancion + ")" +  " VALUES " + "('" + idArtist + "','" + idSong + "');"; 
	}

	private String selectArtista(int idSong){
		return "SELECT " + idArtista + " FROM " + tabla + " WHERE " + idCancion + " = " + idSong + ";";
	}

	private String selectCancion(int idArtist){
		return "SELECT " + idCancion + " FROM " + tabla + " WHERE " + idArtista + " = " + idArtist + ";";
	}

	private String selectTodo(){
		return "SELECT * FROM " + tabla;
	}

	private String selectTodoIdCancion(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idCancion;
	}

	private String selectTodoIdArtista(String id){
		return "SELECT * FROM " + tabla + " WHERE " + id + " = " + idArtista;
	}

	private String joinTodo(){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodo() + ") ON Artistas.id = " + idArtista + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinTodoAnio(String anio){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodo() + ") ON Artistas.id = " + idArtista + " and Canciones.id = " + idCancion + " and año = + " + anio + ";"; 
	}

	private String joinTodoDuracion(String dur){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodo() + ") ON Artistas.id = " + idArtista + " and Canciones.id = " + idCancion + " and duracion = + " + dur + ";"; 
	}

	private String joinCancionesAArtistasID(String id){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodoIdCancion(id) + ") ON Artistas.id = " + idArtista + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinArtistasACancionesID(String id){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodoIdArtista(id) + ") ON Artistas.id = " + idArtista + " and Canciones.id = " + idCancion + ";"; 
	}

	private String joinTodoEntreDur(String dur1, String dur2){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodo() + ") ON Artistas.id = " + idArtista + " and Canciones.id = " + idCancion + " and duracion BETWEEN "  + dur1 + " AND "  + dur2 + ";"; 
	}

	private String joinTodoEntreAnio(String dur1, String dur2){
		return "SELECT cancion,año,duracion,artista FROM Canciones,Artistas JOIN(" + selectTodo() + ") ON Artistas.id = " + idArtista + " and Canciones.id = " + idCancion + " and año BETWEEN "  + dur1 + " AND "  + dur2 + ";"; 
	}

	/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: updateArtista, updateCancion, deleteArtista, deleteCancion, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public synchronized void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "updateArtista":
					comando = updateArtista();
					stmt.executeUpdate(comando);
					break;
				case "updateCancion":
					comando = updateCancion();
					stmt.executeUpdate(comando);
					break;
				case "deleteArtista":
					comando = deleteArtista();
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
	* @param operacion puede recibir cualquiera de estas frases: selectTodo,selectCancion,selectArtista.
	* @param id para saber que se esta buscando.
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ResultSet realizaBusqueda(String operacion,int id){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "selectArtista":
					comando = selectArtista(id);
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
					
					throw new ErrorBaseDeDatos("No conozco esa operacion.");
			}
		}catch(SQLException sqle){
			
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		
		return rs;
	}

	/**
	* Realiza operaciones no triviales de busqueda entre dos tablas.
	* @param operacion puede recibir cualquiera de estas frases: joinCancionesAArtistasIDLike,joinCancionesAArtistasIDAnio,joinCancionesAArtistasIDEntreAnios,joinCancionesAArtistasIDDuracion,joinCancionesAArtistasIDEntreDuraciones,joinArtistasACancionesID.
	* @param param1 Se le pasan los parametros para realizar la busqueda.
	* @param param2 Se le pasan los parametros para realizar la busqueda. 
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public LinkedList<ResultSet> realizaBusquedaEspecial(String operacion, String param1,String param2){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		ResultSet rs1 = null,rs = null;
		LinkedList<ResultSet> lrs = new LinkedList<ResultSet>();
		Canciones can = new Canciones();
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "joinTodo":
						comando = joinTodo();
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
					case "joinTodoAnio":
						comando = joinTodoAnio(param1);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
					case "joinTodoDuracion":
						comando = joinTodoDuracion(param1);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
					case "joinTodoEntreDur":
						comando = joinTodoEntreDur(param1,param2);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
					case "joinTodoEntreAnio":
						comando = joinTodoEntreAnio(param1,param2);
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					break;
				case "joinCancionesAArtistasIDLike":
					rs = can.realizaBusqueda("selectLikeID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAArtistasIDAnio":
					rs = can.realizaBusqueda("selectAnioID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						System.out.println(iden);
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAArtistasIDEntreAnios":
					rs = can.realizaBusqueda("selectEntreAniosID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAArtistasIDDuracion":
					rs = can.realizaBusqueda("selectDuracionID",0,param1,""); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinCancionesAArtistasIDEntreDuraciones":
					rs = can.realizaBusqueda("selectEntreDuracionesID",0,param1,param2); //metodo de clase Canciones.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinCancionesAArtistasID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}
					break;
				case "joinArtistasACancionesID":
					Artistas art = new Artistas();
				    rs = art.realizaBusqueda("selectLikeID",0,param1); //metodo de clase Artistas.java
					while(rs.next()){
						int iden = rs.getInt("id");
						comando = joinArtistasACancionesID(Integer.toString(iden));
						rs1 = stmt.executeQuery(comando);
						lrs.add(rs1);
					}	
					break;
				default:
				
				throw new ErrorBaseDeDatos("No conozco esa operacion."); 
			}
		}catch(SQLException sqle){
			
			throw new ErrorBaseDeDatos("Algo paso.");
		}
		
		return lrs;
	}
	
}