package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

/**
*	Clase que modela la tabla de Relaciones entre Artistas.
*/
public class Artistas{


	private static String tabla = "Artistas";
	private static String id = "id";
	private static String artista = "artista";
	private String artist;
	private int idArtist;

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idArtist identificador de este renglon. 
	* @param artist nombre del artista.
	*/
	public Artistas(int idArtist, String artist){
		this.idArtist = idArtist;
		this.artist = artist;
	}

	/**
	* Constructor vacio.
	*/
	public Artistas(){}

	private String update(){
		return "UPDATE " + tabla + " SET " + artista + "='" + artist + "' WHERE " + id + " = " + idArtist + ";";
	}

	private String delete(){
		return "DELETE FROM " + tabla + " WHERE " + artista + " = '" + artist + "';";  
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + id + "," + artista + ") " +  " VALUES " + "('" + idArtist + "','" + artist + "');"; 
	}

	private String select(int idArtist){
		return "SELECT " + artista + " FROM " + tabla + " WHERE " + id + " = " + idArtist + ";";
	}

	private String selectTodo(){
		return "SELECT " + artista + " FROM " + tabla +";";
	}

	private String selectTodoID(){
		return "SELECT * FROM " + tabla +";";
	}

	private String selectLike(String artist){
		return "SELECT " + artista + " FROM " + tabla + " WHERE lower(" + artista + ") LIKE '%" + artist.toLowerCase() + "%';";
	}

	private String selectLikeID(String artist){
		return "SELECT * FROM " + tabla + " WHERE lower(" + artista + ") LIKE '%" + artist.toLowerCase() + "%';";
	}
 

	/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: update, delete, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public synchronized void realizaOperacion(String operacion){
		String comando = "";
		Statement stmt = null;
		Connection conexion = Manejador.abrirConexion();
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "update":
					comando = update();
					stmt.executeUpdate(comando);
					break;
				case "delete":
					comando = delete();
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
	* @param operacion puede recibir cualquiera de estas frases: select, selectTodo,selectLike.
	* @param id se requiere para la operacion select.
	* @param param1 Terminos de la busqueda de las dos tablas.
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ResultSet realizaBusqueda(String operacion,int id, String param1){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conexion.createStatement();
			switch(operacion){
				case "select":
				comando = select(id);
					rs = stmt.executeQuery(comando);
					break;
				case "selectTodo":
					comando = selectTodo();
					rs = stmt.executeQuery(comando);
					break;
				case "selectLike":
					comando = selectLike(param1);
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
}