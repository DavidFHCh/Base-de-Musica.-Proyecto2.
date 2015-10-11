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
		return "UPDATE " + tabla + " SET " + artista + "='" + this.artist + "' WHERE " + id + " = " + this.idArtist + ";";
	}

	public String delete(){
		return "DELETE FROM " + tabla + " WHERE " + artista + " = '" + this.artist + "';"  
	}

	public String insert(){
		return "INSERT INTO " + tabla "(" + artista + ") " +  " VALUES " + "('" + this.artist + "');"; 
	}

	public String select(){
		return "SELECT " + artista + " FROM " tabla + " WHERE " + this.id " = " + id + ";";
	}

	public String selectTodo(){
		return "SELECT " + artista + " FROM " tabla ";";
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
				throw new ErrorBaseDeDatos("No conozco esa operacion.");
		}
		Manejador.cerrarConexion();
	}

}