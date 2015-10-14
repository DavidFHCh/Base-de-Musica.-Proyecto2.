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

	public Artistas(int idArtist, String artist){
		this.idArtist = idArtist;
		this.artist = artist;
	}

	public Artistas(){}

	public String update(){
		return "UPDATE " + tabla + " SET " + artista + "='" + artist + "' WHERE " + id + " = " + idArtist + ";";
	}

	public String delete(){
		return "DELETE FROM " + tabla + " WHERE " + artista + " = '" + artist + "';";  
	}

	public String insert(){
		return "INSERT INTO " + tabla + "(" + id + "," + artista + ") " +  " VALUES " + "('" + idArtist + "','" + artist + "');"; 
	}

	public String select(int idArtist){
		return "SELECT " + artista + " FROM " + tabla + " WHERE " + id + " = " + idArtist + ";";
	}

	public String selectTodo(){
		return "SELECT " + artista + " FROM " + tabla +";";
	}

	public String selectLike(String artist){
		return "SELECT " + artista + " FROM " + tabla + " WHERE lower(" + artista + ") LIKE '%" + artist.toLowerCase() + "%';";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Statement stmt = null;
		Connection conexion = Manejador.abrirConexion(false);
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

	public ResultSet realizaBusqueda(String operacion,int id, String param1){
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
					comando = selectLike(param1);
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