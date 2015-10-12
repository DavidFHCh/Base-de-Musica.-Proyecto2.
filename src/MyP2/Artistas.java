package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Artistas{

	private static String tabla = "Artistas";
	private static String id = "id";
	private static String artista = "artista";
	private String artist;
	private int idArtist;

	public Artista(int idArtist, String artist){
		this.idArtist = idArtist;
		this.artist = artist;
	}

	public String update(){
		return "UPDATE " + tabla + " SET " + artista + "='" + artist + "' WHERE " + id + " = " + idArtist + ";";
	}

	public String delete(){
		return "DELETE FROM " + tabla + " WHERE " + artista + " = '" + artist + "';"  
	}

	public String insert(){
		return "INSERT INTO " + tabla "("+ id + "," + artista + ") " +  " VALUES " + "('" + idArtist + "','" + artist + "');"; 
	}

	public String select(){
		return "SELECT " + artista + " FROM " tabla + " WHERE " + id " = " + idArtist + ";";
	}

	public String selectTodo(){
		return "SELECT " + artista + " FROM " + tabla +";";
	}

	public String selectLike(){
		return "SELECT " + artista + " FROM " + tabla + " WHERE lower(" + artista + ") LIKE '%" + artist.toLowerCase() "%';";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
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
		Manejador.cerrarConexion();
	}

	public ResultSet realizaBusqueda(String operacion){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
		ResultSet rs = null;
		switch(operacion){
			case "select":
			comando = select();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectTodo":
				comando = selectTodo();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectLike":
				comando = selectLike();
				rs = stmt.executeUpdate();
				break;
			default:
				Manejador.cerrarConexion();
				throw new ErrorBaseDeDatos("No conozco esa operacion.");
		}
		Manejador.cerrarConexion();
		return rs;
	}
}