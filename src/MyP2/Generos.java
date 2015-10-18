package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;


/**
*	Clase que modela la tabla de Relaciones entre Generos.
*/
public class Generos{


	private static String tabla = "Generos";
	private static String id = "id";
	private static String genero = "generos";
	private String genre;
	private int idGenre;

	/**
	* Constructor para modelar un renglon de la tabla.
	* @param idGenre identificador de este renglon. 
	* @param genre nombre del artista.
	*/
	public Generos(int idGenre, String genre){
		this.idGenre = idGenre;
		this.genre = genre;
	}

	/**
	* Constructor vacio.
	*/
	public Generos(){}

	private String update(){
		return "UPDATE " + tabla + " SET " + genero + " =' " + genre + "' WHERE " + id + " = " + idGenre + ";";
	}

	private String delete(){
		return "DELETE FROM " + tabla + " WHERE " + genero + " = '" + genre + "';";  
	}

	private String insert(){
		return "INSERT INTO " + tabla + "(" + id + "," + genero + ") " +  " VALUES " + "('" + idGenre + "','" + genre + "');";
	}

	private String select(int idGenre){
		return "SELECT " + genero + " FROM " + tabla + " WHERE " + id + " = " + idGenre + ";";
	}

	private String selectTodo(){
		return "SELECT * FROM " + tabla + ";";
	}

	private String selectLike(String genre){
		return "SELECT " + genero + " FROM " + tabla + " WHERE lower(" + genero + ") LIKE '%" + genre.toLowerCase() + "%';";
	}


	public String selectLikeID(String genre){
		return "SELECT * FROM " + tabla + " WHERE lower(" + genero + ") LIKE '%" + genre.toLowerCase() + "%';";
	}


	/**
	* Realiza operaciones que modifican la tabla.
	* @param operacion puede recibir cualquiera de estas frases: update, delete, insert.
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public synchronized void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion(false);
		Statement stmt = null;
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
	* @param param Terminos de la busqueda de las dos tablas.
	* @return ResultSet 
	* @throws ErrorBaseDeDatos si no se puede realizar la operacion.
	*/
	public ResultSet realizaBusqueda(String operacion, int id, String param){
		String comando ="";
		Connection conexion = Manejador.abrirConexion(false);
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
					comando = selectLike(param);
					rs = stmt.executeQuery(comando);
					break;
				case "selectLikeID":
					comando = selectLikeID(param);
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
		Manejador.cerrarConexion();
		return rs;
	}
}