package MyP2;

import java.sql.*;
import java.util.*;
import org.sqlite.*;

public class Generos_Cancion{

	private static String tabla = "Generos_Cancion";
	private static String idGenero = "id_genero";
	private static String idCancion = "id_cancion";
	private int idGenre;
	private int idSong;

	public Generos_Cancion(int idGenre, int idSong){
		this.idGenre = idGenre;
		this.idSong = idSong;
	}

	public String updateGenero(){
		return "UPDATE " + tabla + " SET " + idGenero + " ='" + idGenre + "' WHERE " + idSong + " = " + idCancion + ";";
	}

	public String updateCancion(){
		return "UPDATE " + tabla + " SET " + idCancion + " ='" + idSong + "' WHERE " + idGenero + " = " + idGenre + ";";
	}

	public String deleteGenero(){
		return "DELETE FROM " + tabla + " WHERE " + idGenero + " = '" + idGenre + "';"  
	}

	public String deleteCancion(){
		return "DELETE FROM " + tabla + " WHERE " + idCancion + " = '" + idSong + "';"  
	}

	public String insert(){
		return "INSERT INTO " + tabla "(" + idGenero + "," + idCancion + ") " +  " VALUES " + "('" + idGenre + "','" + idSong + "');"; 
	}

	public String selectGenero(){
		return "SELECT " + idGenero + " FROM " tabla + " WHERE " + idCancion " = " + idSong + ";";
	}

	public String selectCancion(){
		return "SELECT " + idCancion + " FROM " tabla + " WHERE " + idGenero " = " + idGenre + ";";
	}

	public String selectTodo(){
		return "SELECT * FROM " tabla ";";
	}

	public void realizaOperacion(String operacion){
		String comando = "";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
		switch(operacion){
			case "updateGenero":
				comando = updateGenero();
				stmt.executeUpdate(comando);
				break;
			case "updateCancion":
				comando = updateCancion();
				stmt.executeUpdate(comando);
				break;
			case "deleteGenero":
				comando = deleteGenero();
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
		Manejador.cerrarConexion();
	}

	public ResultSet realizaBusqueda(String operacion){
		String comando ="";
		Connection conexion = Manejador.abrirConexion();
		Statement stmt = conexion.createStatement();
		ResultSet rs = null;
		switch(operacion){
			case "selectGenero":
				comando = selectGenero();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectCancion":
				comando = selectCancion();
				rs = stmt.executeUpdate(comando);
				break;
			case "selectTodo":
				comando = selectTodo();
				rs = stmt,executeUpdate(comando);
				break;
			default:
				Manejador.cerrarConexion();
				throw new ErrorBaseDeDatos("No conozco esa operacion.");
		}
		return rs;
	}
	
}